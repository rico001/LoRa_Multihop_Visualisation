package de.htwberlin.lora_multihop_logic.components.parser;

import android.util.Log;

import de.htwberlin.lora_multihop_logic.components.messages.AckMessage;
import de.htwberlin.lora_multihop_logic.components.messages.JoinMessage;
import de.htwberlin.lora_multihop_logic.components.messages.JoinReplyMessage;
import de.htwberlin.lora_multihop_logic.components.messages.Message;
import de.htwberlin.lora_multihop_logic.components.model.LocalHop;
import de.htwberlin.lora_multihop_logic.components.queue.IncomingMessageQueue;
import de.htwberlin.lora_multihop_logic.enums.EMessageType;
import de.htwberlin.lora_multihop_logic.interfaces.ILoraCommands;

import static java.lang.Double.parseDouble;

/**
 * This class parses Strings to instances of Messages and adds them to the queue
 *
 * @author morelly_t1
 */
public class MessageParser {

    private static final String TAG = "MessageParser";

    private ILoraCommands executor;

    public MessageParser(ILoraCommands executor) {
        this.executor = executor;
    }

    public void parseInput(String inputString) throws ParserException, IndexOutOfBoundsException, NumberFormatException, NullPointerException {

        IncomingMessageQueue queue = IncomingMessageQueue.getInstance();
        String[] inputParts = inputString.split(",");
        Message message;

        Log.i(TAG, "Parsing String " + inputParts[3]);

        if (inputParts[3].contains(EMessageType.JOIN.name())) {
            message = parseJoinMessage(inputParts);
            Log.i(TAG, "successfully parsed JOIN message");
            queue.add(message);
            Log.i(TAG, "added " + message.toString() + " to queue");
        } else if (inputParts[3].contains(EMessageType.JORP.name())) {
            message = parseJoinReplyMessage(inputParts);
            Log.i(TAG, "successfully parsed JOIN_REPLY message");
            queue.add(message);
            Log.i(TAG, "added " + message.toString() + " to queue");
        } else if (inputString.contains(EMessageType.ACK.name())) {
            message = parseAckMessage(inputParts);
            Log.i(TAG, "successfully parsed ACK message");
            queue.add(message);
            Log.i(TAG, "added " + message.toString() + " to queue");
        } else {
            Log.e(TAG, "couldnt determine which Message type " + inputString + " is.");
            throw new ParserException("no message to queue");
        }

    }

    private Message parseJoinMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException, NumberFormatException {
        if (inputParts.length != 6)
            throw new ParserException("Couldnt Parse Join Message", new Throwable(EMessageType.JOIN.name()));

        String sourceAddress = inputParts[1];
        double latitude = parseDouble(inputParts[4]);
        double longitude = parseDouble(inputParts[5]);

        return new JoinMessage(executor, sourceAddress, latitude, longitude);
    }

    private Message parseJoinReplyMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException, NumberFormatException {
        if (inputParts.length != 6)
            throw new ParserException("Couldnt Parse Join Reply Message", new Throwable(EMessageType.JORP.name()));

        // LR,0001,13,JORP,52.3321,30.313

        String sourceAddress = inputParts[1];
        double latitude = parseDouble(inputParts[4]);
        double longitude = parseDouble(inputParts[5]);

        return new JoinReplyMessage(executor, sourceAddress, latitude, longitude);

    }

    private Message parseAckMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException, NumberFormatException {
        if (inputParts.length != 4)
            throw new ParserException("Couldnt Parse Ack Reply Message", new Throwable(EMessageType.ACK.name()));

        String sourceAddress = inputParts[1];

        return new AckMessage(executor, sourceAddress);

    }
}
