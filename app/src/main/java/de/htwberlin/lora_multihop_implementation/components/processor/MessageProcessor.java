package de.htwberlin.lora_multihop_implementation.components.processor;

import android.util.Log;

import de.htwberlin.lora_multihop_implementation.components.messages.JoinMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.JoinReplyMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.Message;
import de.htwberlin.lora_multihop_implementation.components.queue.IncomingMessageQueue;
import de.htwberlin.lora_multihop_implementation.components.queue.OutgoingMessageQueue;
import de.htwberlin.lora_multihop_implementation.enums.EMessageType;
import de.htwberlin.lora_multihop_implementation.interfaces.ILoraCommands;
import de.htwberlin.lora_multihop_implementation.interfaces.MessageConstants;
import de.htwberlin.lora_multihop_visualisation.SingletonDevice;

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
    private OutgoingMessageQueue outgoingMessageQueue = OutgoingMessageQueue.getInstance();

    public MessageProcessor(ILoraCommands executor) {
        this.executor = executor;
    }

    public void processMessage() {
        Message incomingMessage = (Message) incomingMessageQueue.poll();
        EMessageType answerMessage = incomingMessage.getAnswerMessage();

        Log.i(TAG, "Processing " + incomingMessage.toString());
        if (answerMessage == null) {
            Log.i(TAG, "Processed Message results in a neighbour set action");
            // updateNSEntry
            // addNSEntry

        } else {
            if (incomingMessage instanceof JoinMessage) {

                // REPLY: JOIN REPLY
                JoinReplyMessage joinReplyMessage = new JoinReplyMessage(executor, "9341", "2222", 50.2332, 40.1221);
                outgoingMessageQueue.add(joinReplyMessage);
                Log.i(TAG, "added jorp message to queue");
            } else if (incomingMessage instanceof JoinReplyMessage) {
                // REPLY: ACK
            } else {

            }
        }
    }

    public void sendReplyMessage() {
        Message outgoingMessage = (Message) outgoingMessageQueue.poll();

        outgoingMessage.executeAtRoutine();
        //Log.i(TAG, "sended " + outgoingMessage.toString() + " to " + outgoingMessage.getRemoteAddress());
    }
}
