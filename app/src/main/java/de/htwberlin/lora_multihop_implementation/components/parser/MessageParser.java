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
import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

/**
 * This class parses Strings to instances of Messages and adds them to the queue
 *
 * @author morelly_t1
 */
public class MessageParser {

    private static final String TAG = "MessageParser";

    public static Message parseInput(String inputString){

        String[] inputParts = inputString.split(";");
        Message message = null;

        try {
            if (inputParts[0] == "JOIN") {
                message = parseJoinMessage(inputParts);
            } else if (inputParts[0] == "JOIN_REPLY") {
                message = parseJoinReplyMessage(inputParts);
            } else if (inputParts[0] == "PULL") {
                message = parsePullMessage(inputParts);
            } else if (inputParts[0] == "PUSH") {
                message = parsePushMessage(inputParts);
            } else if (inputParts[0] == "MOVE") {
                message = parseMoveMessage(inputParts);
            } else if (inputParts[0] == "LEAVE") {
                message = parseLeaveMessage(inputParts);
            } else if (inputParts[0] == "FETCH") {
                message = parseFetchMessage(inputParts);
            } else if (inputParts[0] == "FETCH_REPLY") {
                message = parseFetchReplyMessage(inputParts);
            } else if (inputParts[0] == "ACK") {
                message = parseAckMessage(inputParts);
            } else {
                Log.e(TAG, "couldnt determine which Message type " + inputString + " is.");
            }
        } catch (IndexOutOfBoundsException e ){
            Log.e(TAG, "Index Out of Bounds, while parsing  ");
        } catch (ParserException e) {
            Log.e(TAG, "Error while parsing " + e.getCause());
        } finally {
            return message;
        }
    }

   private static Message parseJoinMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException {
        if (inputParts.length != 4) throw new ParserException("Couldnt Parse Message", new Throwable(EMessageType.JOIN.name()));

        String sourceAddress = inputParts[1];
        double latitude = Double.parseDouble(inputParts[2]);
        double longitude = Double.parseDouble(inputParts[3]);

        return new JoinMessage(sourceAddress, latitude, longitude);
    }


    private static Message parseJoinReplyMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException {
        if (inputParts.length != 4) throw new ParserException("Couldnt Parse Message", new Throwable(EMessageType.JOIN_REPLY.name()));

        String sourceAddress = inputParts[1];
        double latitude = Double.parseDouble(inputParts[2]);
        double longitude = Double.parseDouble(inputParts[3]);

        return new JoinReplyMessage(sourceAddress, latitude, longitude);
    }

    private static Message parseFetchMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException {
        if (inputParts.length != 2) throw new ParserException("Couldnt Parse Message", new Throwable(EMessageType.FETCH.name()));

        String sourceAddress = inputParts[1];

        return new FetchMessage(sourceAddress);
    }

    private static Message parseFetchReplyMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException {

        // sourceAddress; N; checksum_for_each_N; -> N + 2;
        int amountOfChecksums = Integer.parseInt(inputParts[2]);
        int necessaryLength = amountOfChecksums + 2;
        if (inputParts.length != necessaryLength) throw new ParserException("Couldnt Parse Message", new Throwable(EMessageType.FETCH_REPLY.name()));

        String sourceAddress = inputParts[1];
        String[] checksums = new String[amountOfChecksums];

        for (int i = 0; i < checksums.length; i++){
            checksums[i] = ""; // TODO: generateChecksum method & receiving NS entry
        }

        return new FetchReplyMessage(sourceAddress, amountOfChecksums, checksums);
    }

    private static Message parsePullMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException {

        if (inputParts.length != 2) throw new ParserException("Couldnt Parse Message", new Throwable(EMessageType.PULL.name()));

        String sourceAddress = inputParts[1];
        String checksum = inputParts[2];

        return new PullMessage(sourceAddress, checksum);
    }

    private static Message parsePushMessage(String[] inputParts)throws ParserException, IndexOutOfBoundsException {

        if (inputParts.length != 6) throw new ParserException("Couldnt Parse Message", new Throwable(EMessageType.PUSH.name()));

        String sourceAddress = inputParts[1];
        String sourceAddresHop = inputParts[2];
        String directAttachedHop = inputParts[3];
        Double latitude = Double.parseDouble(inputParts[4]);
        double longitude = Double.parseDouble(inputParts[5]);

        return new PushMessage(sourceAddress, sourceAddresHop, directAttachedHop, latitude, longitude);

    }

    private static Message parseLeaveMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException {

        if (inputParts.length != 2) throw new ParserException("Couldnt Parse Message", new Throwable(EMessageType.LEAVE.name()));

        String sourceAddress = inputParts[1];

        return new LeaveMessage(sourceAddress);
    }

    private static Message parseMoveMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException {

        if (inputParts.length != 4) throw new ParserException("Couldnt Parse Message", new Throwable(EMessageType.MOVE.name()));

        String sourceAddress = inputParts[1];
        double latitude = Double.parseDouble(inputParts[2]);
        double longitude = Double.parseDouble(inputParts[3]);

        return new MoveMessage(sourceAddress,latitude,longitude);
    }

    private static Message parseAckMessage(String[] inputParts) throws ParserException, IndexOutOfBoundsException {

        if (inputParts.length != 2) throw new ParserException("Couldnt Parse Message", new Throwable(EMessageType.ACK.name()));

        String sourceAddress = inputParts[1];

        return new AckMessage(sourceAddress);
    }
}
