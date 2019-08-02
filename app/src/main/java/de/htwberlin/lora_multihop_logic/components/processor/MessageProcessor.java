package de.htwberlin.lora_multihop_logic.components.processor;

import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import de.htwberlin.lora_multihop_logic.components.messages.FetchMessage;
import de.htwberlin.lora_multihop_logic.components.messages.FetchReplyMessage;
import de.htwberlin.lora_multihop_logic.components.messages.JoinMessage;
import de.htwberlin.lora_multihop_logic.components.messages.JoinReplyMessage;
import de.htwberlin.lora_multihop_logic.components.messages.Message;
import de.htwberlin.lora_multihop_logic.components.messages.PullMessage;
import de.htwberlin.lora_multihop_logic.components.messages.PushMessage;
import de.htwberlin.lora_multihop_logic.components.processor.exchange.ExchangeHandler;
import de.htwberlin.lora_multihop_logic.components.processor.exchange.FetchExchangeHandler;
import de.htwberlin.lora_multihop_logic.components.processor.exchange.JoinExchangeHandler;
import de.htwberlin.lora_multihop_logic.components.processor.exchange.PullPushExchangeHandler;
import de.htwberlin.lora_multihop_logic.interfaces.ILoraCommands;
import de.htwberlin.lora_multihop_logic.interfaces.MessageConstants;

/**
 * This class removes messages from queue and processes them.
 * A message either results in sending an answer message or adding / updating an entry to NS
 *
 * @author morelly_t1
 */

public class MessageProcessor implements MessageConstants {
    private static final String TAG = "MessageProcessor";

    private ILoraCommands executor;
    private BlockingQueue<Message> outMessagesQueue = new LinkedBlockingQueue<>();

    private ExecutorService outWorker;

    // todo: need to iterate over the map and check if there are any "expired" (i.e. startTimestamp < now - 5minutes) handlers. destroy them to free the memory, write a line in the log.

    // I'd go for a singleton manager class.. state machine... shared buffer map
    // buffer queue for last 20 msgs
    // checks if already msg received from incoming source / remote adress
    // which communication was inited? -> join? updateTableDataRoutine? ...

    // now also... new exchange handler for every message, even it's part of exchange (join reply between join / ack)
    // better? --> updated exchange objects (states: JOIN -> JOIN REPLY -> ACK UPDATE)
    private HashMap<String, ExchangeHandler> handlersMap = new HashMap<>();

    public MessageProcessor(ILoraCommands executor) {
        this.executor = executor;

        this.outWorker = Executors.newSingleThreadExecutor();
        this.outWorker.submit(new OutWorker());
    }

    public BlockingQueue<Message> getOutMessagesQueue() {
        return outMessagesQueue;
    }

    /**
     * Starts the JOIN exchange with the given coordinates.
     */
    public void startJoinExchange(double lng, double lat) {
        JoinExchangeHandler exchange = new JoinExchangeHandler(outMessagesQueue, lng, lat);
        handlersMap.put(exchange.getId(), exchange);
        exchange.sendInitMessage();
    }

    public void processMessage(Message incomingMessage) {
        // todo: No message should be with null id, new message must be initalized only in ExchangeHandler
        String id = incomingMessage.getId();
        ExchangeHandler handler = handlersMap.get(id);

        // Init a new handler as a replier.
        if (handler == null) {
            try {
                handlersMap.put(id, initHandler(incomingMessage));
            } catch (Exception e) {
                Log.i(TAG, e.getMessage());
                return;
            }
        }

        // Process the message, do the needed work, queue a reply message, etc.
        handler.processMessage(incomingMessage);
    }

    private ExchangeHandler initHandler(Message message) throws Exception {
        Log.i(TAG, "Creating new handler with exchange id " + message.getId());

        if (message instanceof JoinMessage || message instanceof JoinReplyMessage) {
            return new JoinExchangeHandler(outMessagesQueue, message);
        }
        if (message instanceof PullMessage || message instanceof PushMessage) {
            return new PullPushExchangeHandler(outMessagesQueue, message);
        }
        if (message instanceof FetchMessage || message instanceof FetchReplyMessage) {
            return new FetchExchangeHandler(outMessagesQueue, message);
        }

        throw new Exception("Unknown message type" + message.getClass().getName());
    }

    /**
     * A worker running in a separate thread that sends queued the messages to the connected LoRa module.
     * There must be just ONE worker simultaneously, as LoRa module can process just one message at a time.
     */
    private class OutWorker implements Runnable {
        public void run() {
            int pauseMs = 100;

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // todo: timeouts between messages?
                    // todo: remote address should be in the exchange handler? when should it be set? figure out.
                    Message message = outMessagesQueue.take();
                    int size = message.toString().getBytes().length;

                    Log.i(TAG, "Out worker: processing message '" + message.toString() + "' of size " + size);

                    try {
                        executor.setTargetAddress(message.getRemoteAddress());
                        Thread.sleep(pauseMs);
                        executor.send(size);
                        Thread.sleep(pauseMs);
                        executor.send(message.toString());
                        Thread.sleep(pauseMs);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Log.i(TAG, "sent '" + message.toString() + "' to " + message.getRemoteAddress());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}