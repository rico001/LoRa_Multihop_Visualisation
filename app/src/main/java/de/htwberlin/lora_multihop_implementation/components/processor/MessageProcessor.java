package de.htwberlin.lora_multihop_implementation.components.processor;

import android.os.Handler;
import android.util.Log;

import de.htwberlin.lora_multihop_implementation.components.lora.LoraCommandsExecutor;
import de.htwberlin.lora_multihop_implementation.components.messages.JoinMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.JoinReplyMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.Message;
import de.htwberlin.lora_multihop_implementation.components.queue.IncomingMessageQueue;
import de.htwberlin.lora_multihop_implementation.components.queue.OutgoingMessageQueue;
import de.htwberlin.lora_multihop_implementation.enums.EMessageType;
import de.htwberlin.lora_multihop_implementation.interfaces.ILoraCommands;
import de.htwberlin.lora_multihop_implementation.interfaces.MessageConstants;
import de.htwberlin.lora_multihop_visualisation.BluetoothService;
import de.htwberlin.lora_multihop_visualisation.SingletonDevice;

/**
 * This class removes messages from queue and processes them.
 * A message either results in sending an answer message or adding / updating an entry to NS
 * @author morelly_t1
 */

public class MessageProcessor implements MessageConstants {

    private static final String TAG = "MessageProcessor";

    private ILoraCommands executor;

    public MessageProcessor(ILoraCommands executor) {
        this.executor = executor;
    }

    public void processMessage() {

        IncomingMessageQueue incomingMessageQueue = IncomingMessageQueue.getInstance();
        OutgoingMessageQueue outgoingMessageQueue = OutgoingMessageQueue.getInstance();

        Message incomingMessage = (Message) incomingMessageQueue.poll();
        EMessageType answerMessage = incomingMessage.getAnswerMessage();

        Log.i(TAG, "Processing " + incomingMessage.toString());
        if (answerMessage == null) {
            Log.i(TAG, "Processed Message results in a neighbour set action");
            // updateNSEntry
            // addNSEntry

        } else {

            try {

                Thread.sleep(1000);
                executor.setTargetAddress("FFFF");
                Thread.sleep(1000);
                executor.send(5);
                Thread.sleep(1000);
                executor.send("12345");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "Sending reply msg ");
        }
    }

}
