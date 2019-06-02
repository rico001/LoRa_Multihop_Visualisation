package de.htwberlin.lora_multihop_implementation.components.parser;

import android.util.Log;

import java.util.regex.Pattern;

import de.htwberlin.lora_multihop_implementation.components.messages.JoinMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.JoinReplyMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.Message;
import de.htwberlin.lora_multihop_implementation.components.queue.IncomingMessageQueue;
import de.htwberlin.lora_multihop_implementation.enums.EMessageType;
import de.htwberlin.lora_multihop_implementation.interfaces.ILoraCommands;

import static java.lang.Double.parseDouble;
import static java.util.regex.Pattern.compile;

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

        // Message = "LR,Source Address, Message length, Message Type, Params...."
        // inputString = "LR,2222,12,JOIN,52.323,40.121

        IncomingMessageQueue queue = IncomingMessageQueue.getInstance();

        String[] inputParts = inputString.split(",");
        Message message;

        // TODO: verifiy latitude / longitude between 0 - 180.00 and 3 decimal digits
        //TODO: template pattern maybe can be used here ...
        switch (inputParts[3]) {
            case "JOIN":
                message = parseJoinMessage(inputParts);
                Log.i(TAG, "successfully parsed JOIN message");
                queue.add(message);
                Log.i(TAG, "added " + message.toString() + " to queue");
                break;
            case "JORP":
                message = parseJoinReplyMessage(inputParts);
                Log.i(TAG, "successfully parsed JOIN_REPLY message");
                queue.add(message);
                Log.i(TAG, "added " + message.toString() + " to queue");
                break;
            default:
                Log.e(TAG, "couldnt determine which Message type " + inputString + " is.");
                break;
        }
    }

    protected Message parseJoinMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException, NumberFormatException {
        if (inputParts.length != 6)
            throw new ParserException("Couldnt Parse Join Message", new Throwable(EMessageType.JOIN.name()));

        String sourceAddress = inputParts[1];
        double latitude = parseDouble(inputParts[4]);
        double longitude = parseDouble(inputParts[5]);

        return new JoinMessage(sourceAddress, executor, latitude, longitude);
    }


    private Message parseJoinReplyMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException, NumberFormatException {
        if (inputParts.length != 6)
            throw new ParserException("Couldnt Parse Join Reply Message", new Throwable(EMessageType.JORP.name()));

        String sourceAddress = inputParts[1];
        String remoteAddress = inputParts[2];
        double latitude = parseDouble(inputParts[4]);
        double longitude = parseDouble(inputParts[5]);

        return new JoinReplyMessage(sourceAddress, executor, remoteAddress, latitude, longitude);

    }
}
