package de.htwberlin.lora_multihop_implementation.components.processor;

import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import de.htwberlin.lora_multihop_implementation.components.messages.Message;
import de.htwberlin.lora_multihop_implementation.components.queue.IncomingMessageQueue;
import de.htwberlin.lora_multihop_implementation.interfaces.ILoraCommands;
import de.htwberlin.lora_multihop_implementation.interfaces.MessageConstants;

/**
 * This class removes messages from queue and processes them.
 * A message either results in sending an answer message or adding / updating an entry to NS
 *
 * @author morelly_t1
 */

public class MessageProcessor implements MessageConstants {

    private static final String TAG = "MessageProcessor";

    private ILoraCommands executor;
    private IncomingMessageQueue incomingMessageQueue = IncomingMessageQueue.getInstance();
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

    public void processMessage() {
        Message incomingMessage = (Message) incomingMessageQueue.poll();

        // todo: No message should be with null id, new message must be initalized only in ExchangeHandler
        String id = incomingMessage.getId();
        ExchangeHandler handler = handlersMap.get(id);

        // Init a new handler as a replier.
        if (handler == null) {
            Log.i(TAG, "Creating new handler with exchange id " + id);

            // todo: for now just Join handler. need to create mapping between message classes and their corresponding handler classes
            handler = new JoinExchangeHandler(outMessagesQueue, incomingMessage);
            handlersMap.put(id, handler);
        }

        // Process the message, do the needed work, queue a reply message, etc.
        handler.processMessage(incomingMessage);
    }

    /**
     * A worker running in a separate thread that sends queued the messages to the connected LoRa module.
     * There must be just ONE worker simultaneously, as LoRa module can process just one message at a time.
     */
    private class OutWorker implements Runnable {
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // todo: timeouts between messages?
                    // todo: remote address should be in the exchange handler? when should it be set? figure out.
                    Message message = outMessagesQueue.take();

                    message.setExecutor(executor);

                    Log.i(TAG, "Out worker: processing message " + message.toString());

                    message.executeAtRoutine(executor);

                    Log.i(TAG, "sended " + message.toString() + " to " + message.getRemoteAddress());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
