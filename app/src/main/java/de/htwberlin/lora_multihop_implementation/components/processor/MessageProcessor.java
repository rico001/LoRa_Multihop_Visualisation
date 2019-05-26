package de.htwberlin.lora_multihop_implementation.components.processor;

import android.util.Log;

import de.htwberlin.lora_multihop_implementation.components.messages.Message;
import de.htwberlin.lora_multihop_implementation.components.queue.NeighbourDiscoveryProtocolQueue;
import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

/**
 * This class removes messages from queue and processes them.
 * A message either results in sending an answer message or adding / updating an entry to NS
 * @author morelly_t1
 */

public class MessageProcessor {

    private static final String TAG = "MessageProcessor";

    public static void processQueue(){

        NeighbourDiscoveryProtocolQueue queue = NeighbourDiscoveryProtocolQueue.getInstance();

        Message message = (Message) queue.poll();

        if (message.getAnswerMessage() == null){
            Log.i(TAG, "Processed Message results in a neighbour set action");
            // updateNSEntry
            // addNSEntry

        } else {
            Log.i(TAG, "processed Message results in sending an answerMessage");

            Message answerMessage;
            // sendMessage(answerMessage);
            // TODO: generating answer Messages
            Log.i(TAG, "successfully sended answerMessage");

        }
    }
}
