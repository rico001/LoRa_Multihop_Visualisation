package de.htwberlin.lora_multihop_logic.components.parser;

import android.util.Log;

import java.util.Arrays;

import de.htwberlin.lora_multihop_logic.components.messages.AckMessage;
import de.htwberlin.lora_multihop_logic.components.messages.FetchMessage;
import de.htwberlin.lora_multihop_logic.components.messages.FetchReplyMessage;
import de.htwberlin.lora_multihop_logic.components.messages.JoinMessage;
import de.htwberlin.lora_multihop_logic.components.messages.JoinReplyMessage;
import de.htwberlin.lora_multihop_logic.components.messages.Message;
import de.htwberlin.lora_multihop_logic.components.messages.PullMessage;
import de.htwberlin.lora_multihop_logic.components.messages.PushMessage;
import de.htwberlin.lora_multihop_logic.components.model.LocalHop;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * This class parses Strings to instances of Messages and adds them to the queue
 *
 * @author morelly_t1
 */
public class MessageParser {
    private static final String TAG = "MessageParser";

    /**
     * Instance variable to avoid passing them to each parser method.
     */
    private String sourceAddress, remoteAddress, id;

    public MessageParser() {
    }

    public Message parseInput(String inputString) throws ParserException, IndexOutOfBoundsException, NumberFormatException, NullPointerException {
        // Message = "LR,Source Address, remote address, Message Type, id, Params...."
        // inputString = "LR,2222,12,JOIN,52.323,40.121
        // TODO: verify latitude / longitude between 0 - 180.00 and 3 decimal digits
        // ---> maybe not needed... NeighbourSet Entry checks model data & has Exception calling


        // Reset current parser state.
        sourceAddress = remoteAddress = id = null;

        String[] inputParts = inputString.split(",");

        // Get base message properties: source, remote addresses, type, exchange id.
        String type;
        try {
            sourceAddress = inputParts[1];
            remoteAddress = inputParts[2];
            type = inputParts[3];
            id = inputParts[4];
        } catch (IndexOutOfBoundsException e) {
            throw new ParserException("error by getting base message props: " + e.getMessage());
        }

        // Other props are the message payload.
        String[] payload = Arrays.copyOfRange(inputParts, 5, inputParts.length);

        try {
            switch (type) {
                case "JOIN":
                    return parseJoin(payload);
                case "JORP":
                    return parseJoinReply(payload);
                case "ACK":
                    return parseAck(payload);
                case "PULL":
                    return parsePull(payload);
                case "PUSH":
                    return parsePush(payload);
                case "FETCH":
                    return parseFetch(payload);
                case "FETCH_REPLY":
                    return parseFetchReply(payload);
                default:
                    Log.e(TAG, "couldnt determine which Message type " + inputString + " is.");
                    throw new ParserException("invalid message type: " + type);
            }
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            Log.e(TAG, "error by parsing the string '" + inputString + "'");
            throw new ParserException("error by parsing the string '" + inputString + "': " + e.getMessage());
        }
    }

    private Message parseJoin(String[] payload) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (payload.length != 2)
            throw new IllegalArgumentException("Payload size " + payload.length + " doesn't match required 2");

        double latitude = parseDouble(payload[0]);
        double longitude = parseDouble(payload[1]);

        return new JoinMessage(id, sourceAddress, latitude, longitude);
    }

    private Message parseJoinReply(String[] payload) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (payload.length != 2)
            throw new IllegalArgumentException("Payload size " + payload.length + " doesn't match required 7");

        double latitude = parseDouble(payload[0]);
        double longitude = parseDouble(payload[1]);
        String sourceAddress = LocalHop.getInstance().getAddress();

        return new JoinReplyMessage(id, sourceAddress, remoteAddress, latitude, longitude);
    }

    private Message parseAck(String[] payload) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (payload.length != 0)
            throw new IllegalArgumentException("Body size " + payload.length + " doesn't match required 0");

        String sourceAddress = LocalHop.getInstance().getAddress();

        return new AckMessage(id, sourceAddress, remoteAddress);
    }

    private Message parsePull(String[] payload) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (payload.length != 1)
            throw new IllegalArgumentException("Body size " + payload.length + " doesn't match required 1");

        String checksum = payload[0];

        return new PullMessage(id, sourceAddress, remoteAddress, checksum);
    }

    private Message parsePush(String[] payload) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (payload.length != 4)
            throw new IllegalArgumentException("Body size " + payload.length + " doesn't match required 4");

        String hopAddress = payload[0];
        String attachedHop = payload[1];
        double latitude = parseDouble(payload[2]);
        double longitude = parseDouble(payload[3]);

        return new PushMessage(id, sourceAddress, remoteAddress, hopAddress, attachedHop, latitude, longitude);
    }

    private Message parseFetch(String[] payload) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (payload.length != 0)
            throw new IllegalArgumentException("Body size " + payload.length + " doesn't match required 0");

        return new FetchMessage(id, sourceAddress, remoteAddress);
    }

    private Message parseFetchReply(String[] payload) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (payload.length < 1)
            throw new IllegalArgumentException("Body size " + payload.length + " is less than 1");

        int neighborsCount = parseInt(payload[0]);
        String[] checksums = Arrays.copyOfRange(payload, 1, payload.length);

        if (checksums.length != neighborsCount)
            throw new IllegalArgumentException("Checksums length " + payload.length + " doesn't match specified number of neighbors " + neighborsCount);


        return new FetchReplyMessage(id, sourceAddress, remoteAddress, neighborsCount, checksums);

    }
}