package de.htwberlin.lora_multihop_implementation.components.parser;

import android.util.Log;

import de.htwberlin.lora_multihop_implementation.components.messages.AckMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.FetchMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.FetchReplyMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.JoinMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.JoinReplyMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.LeaveMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.Message;
import de.htwberlin.lora_multihop_implementation.components.messages.MoveMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.PullMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.PushMessage;
import de.htwberlin.lora_multihop_implementation.components.queue.NeighbourDiscoveryProtocolQueue;
import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

import static java.lang.Double.*;

/**
 * This class parses Strings to instances of Messages and adds them to the queue
 *
 * @author morelly_t1
 */
public class MessageParser {

    private static final String TAG = "MessageParser";

    public static void parseInput(String inputString) {

        NeighbourDiscoveryProtocolQueue queue = NeighbourDiscoveryProtocolQueue.getInstance();

        String[] inputParts = inputString.split(";");
        Message message;

        //TODO: template pattern maybe can be used here ...
        try {
            switch(inputParts[0]){
                case "JOIN":
                    message = parseJoinMessage(inputParts);
                    Log.i(TAG, "successfully parsed JOIN message");
                    queue.add(message);
                    Log.i(TAG, "added " + message.toString() + " to queue");
                    break;
                case "JOIN_REPLY":
                    message = parseJoinReplyMessage(inputParts);
                    Log.i(TAG, "successfully parsed JOIN_REPLY message");
                    queue.add(message);
                    Log.i(TAG, "added " + message.toString() + " to queue");
                    break;
                case "FETCH":
                    message = parseFetchMessage(inputParts);
                    Log.i(TAG, "successfully parsed FETCH message");
                    queue.add(message);
                    Log.i(TAG, "added " + message.toString() + " to queue");
                    break;
                case "FETCH_REPLY":
                    message = parseFetchReplyMessage(inputParts);
                    Log.i(TAG, "successfully parsed FETCH_REPLY message");
                    queue.add(message);
                    Log.i(TAG, "added " + message.toString() + " to queue");
                    break;
                case "PULL":
                    message = parsePullMessage(inputParts);
                    Log.i(TAG, "successfully parsed PULL message");
                    queue.add(message);
                    Log.i(TAG, "added " + message.toString() + " to queue");
                    break;
                case "PUSH":
                    message = parsePushMessage(inputParts);
                    Log.i(TAG, "successfully parsed PUSH message");
                    queue.add(message);
                    Log.i(TAG, "added " + message.toString() + " to queue");
                    break;
                case "MOVE":
                    message = parseMoveMessage(inputParts);
                    Log.i(TAG, "successfully parsed MOVE message");
                    queue.add(message);
                    Log.i(TAG, "added " + message.toString() + " to queue");
                    break;
                case "LEAVE":
                    message = parseLeaveMessage(inputParts);
                    Log.i(TAG, "successfully parsed LEAVE message");
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
                    break;
            }
        } catch (IndexOutOfBoundsException e) {
            Log.e(TAG, "Index Out of Bounds, while parsing  ");
        } catch (ParserException e) {
            Log.e(TAG, "Error while parsing " + e.getCause());
        }
    }

    private static Message parseJoinMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException {
        if (inputParts.length != 4)
            throw new ParserException("Couldnt Parse Message", new Throwable(EMessageType.JOIN.name()));

        String sourceAddress = inputParts[1];
        double latitude = parseDouble(inputParts[2]);
        double longitude = parseDouble(inputParts[3]);

        return new JoinMessage(sourceAddress, latitude, longitude);
    }


    private static Message parseJoinReplyMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException {
        if (inputParts.length != 4)
            throw new ParserException("Couldnt Parse Message", new Throwable(EMessageType.JOIN_REPLY.name()));

        String sourceAddress = inputParts[1];
        double latitude = parseDouble(inputParts[2]);
        double longitude = parseDouble(inputParts[3]);

        return new JoinReplyMessage(sourceAddress, latitude, longitude);
    }

    private static Message parseFetchMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException {
        if (inputParts.length != 2)
            throw new ParserException("Couldnt Parse Message", new Throwable(EMessageType.FETCH.name()));

        String sourceAddress = inputParts[1];

        return new FetchMessage(sourceAddress);
    }

    private static Message parseFetchReplyMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException {

        // sourceAddress; N; checksum_for_each_N; -> N + 2;
        int amountOfChecksums = Integer.parseInt(inputParts[2]);
        int necessaryLength = amountOfChecksums + 2;
        if (inputParts.length != necessaryLength)
            throw new ParserException("Couldnt Parse Message", new Throwable(EMessageType.FETCH_REPLY.name()));

        String sourceAddress = inputParts[1];
        String[] checksums = new String[amountOfChecksums];

        for (int i = 0; i < checksums.length; i++) {
            checksums[i] = ""; // TODO: generateChecksum method & receiving NS entry
        }

        return new FetchReplyMessage(sourceAddress, amountOfChecksums, checksums);
    }

    private static Message parsePullMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException {

        if (inputParts.length != 3)
            throw new ParserException("Couldnt Parse Message", new Throwable(EMessageType.PULL.name()));

        String sourceAddress = inputParts[1];
        String checksum = inputParts[2];

        return new PullMessage(sourceAddress, checksum);
    }

    private static Message parsePushMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException {

        if (inputParts.length != 6)
            throw new ParserException("Couldnt Parse Message", new Throwable(EMessageType.PUSH.name()));

        String sourceAddress = inputParts[1];
        String sourceAddresHop = inputParts[2];
        String directAttachedHop = inputParts[3];
        double latitude = parseDouble(inputParts[4]);
        double longitude = parseDouble(inputParts[5]);

        return new PushMessage(sourceAddress, sourceAddresHop, directAttachedHop, latitude, longitude);

    }

    private static Message parseLeaveMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException {

        if (inputParts.length != 2)
            throw new ParserException("Couldnt Parse Message", new Throwable(EMessageType.LEAVE.name()));

        String sourceAddress = inputParts[1];

        return new LeaveMessage(sourceAddress);
    }

    private static Message parseMoveMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException {

        if (inputParts.length != 4)
            throw new ParserException("Couldnt Parse Message", new Throwable(EMessageType.MOVE.name()));

        String sourceAddress = inputParts[1];
        double latitude = parseDouble(inputParts[2]);
        double longitude = parseDouble(inputParts[3]);

        return new MoveMessage(sourceAddress, latitude, longitude);
    }

    private static Message parseAckMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException {

        if (inputParts.length != 2)
            throw new ParserException("Couldnt Parse Message", new Throwable(EMessageType.ACK.name()));

        String sourceAddress = inputParts[1];

        return new AckMessage(sourceAddress);
    }
}
