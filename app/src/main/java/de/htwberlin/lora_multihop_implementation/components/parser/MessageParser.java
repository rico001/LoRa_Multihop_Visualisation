package de.htwberlin.lora_multihop_implementation.components.parser;

import android.util.Log;

import de.htwberlin.lora_multihop_implementation.components.messages.AckMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.JoinMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.JoinReplyMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.Message;
import de.htwberlin.lora_multihop_implementation.components.model.LocalHop;
import de.htwberlin.lora_multihop_implementation.components.queue.IncomingMessageQueue;
import de.htwberlin.lora_multihop_implementation.enums.EMessageType;
import de.htwberlin.lora_multihop_implementation.interfaces.ILoraCommands;
import de.htwberlin.lora_multihop_visualisation.SingletonDevice;

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

        // Message = "LR,Source Address, Message length, Message Type, Params...."
        // inputString = "LR,2222,12,JOIN,52.323,40.121

        IncomingMessageQueue queue = IncomingMessageQueue.getInstance();

        String[] inputParts = inputString.split(",");
        Message message;

        // TODO: verify latitude / longitude between 0 - 180.00 and 3 decimal digits

        // ---> maybe not needed... NeighbourSet Entry checks model data & has Exception calling

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
            case "ACK":
                message = parseAckMessage(inputParts);
                Log.i(TAG, "successfully parsed ACK message");
                queue.add(message);
                Log.i(TAG, "added " + message.toString() + " to queue");
                break;
            default:
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
            throw new ParserException("Couldnt Parse Join Reply Message", new Throwable(EMessageType.JORP
                    .name()));

        String sourceAddress = LocalHop.getInstance().getAddress();
        String remoteAddress = inputParts[2];
        double latitude = parseDouble(inputParts[4]);
        double longitude = parseDouble(inputParts[5]);

        return new JoinReplyMessage(executor, sourceAddress, remoteAddress, latitude, longitude);

    }

    private Message parseAckMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException, NumberFormatException {
        if (inputParts.length != 5)
            throw new ParserException("Couldnt Parse Ack Reply Message", new Throwable(EMessageType.ACK.name()));

        String sourceAddress = LocalHop.getInstance().getAddress();
        String remoteAddress = inputParts[2];

        return new AckMessage(executor, sourceAddress, remoteAddress);

    }
}
