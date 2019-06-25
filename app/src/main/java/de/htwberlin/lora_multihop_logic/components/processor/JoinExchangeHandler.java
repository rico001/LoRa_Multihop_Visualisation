package de.htwberlin.lora_multihop_logic.components.processor;

import android.location.Location;
import android.util.Log;

import java.security.InvalidParameterException;
import java.util.Queue;

import de.htwberlin.lora_multihop_logic.NeighbourSetDataHandler;
import de.htwberlin.lora_multihop_logic.components.messages.AckMessage;
import de.htwberlin.lora_multihop_logic.components.messages.JoinMessage;
import de.htwberlin.lora_multihop_logic.components.messages.JoinReplyMessage;
import de.htwberlin.lora_multihop_logic.components.messages.Message;
import de.htwberlin.lora_multihop_logic.components.model.LocalHop;
import de.htwberlin.lora_multihop_logic.components.model.NeighbourSet;
import de.htwberlin.lora_multihop_logic.enums.ELoraNodeState;

import static android.support.constraint.Constraints.TAG;

public class JoinExchangeHandler extends ExchangeHandler {

    private Double localNodeLng;
    private Double localNodeLat;

    private Double remoteNodeLng;
    private Double remoteNodeLat;

    /**
     * Generates a new init message and enqueues it.
     * Should be invoked as a result of the user-triggered event "Want to connect" (is it a button? idk).
     */
    JoinExchangeHandler(Queue<Message> queue, Double lng, Double lat, NeighbourSetDataHandler neighbourSetDataHandler) {
        super(queue, neighbourSetDataHandler);

        this.localNodeLat = lat;
        this.localNodeLng = lng;
    }

    JoinExchangeHandler(Queue<Message> queue, Message receivedMessage, NeighbourSetDataHandler neighbourSetDataHandler) {
        super(queue, receivedMessage, neighbourSetDataHandler);
    }

    @Override
    protected Message getStartMessage() {
        return new JoinMessage(this.id, localNodeLng, localNodeLat);
    }

    @Override
    protected Message getReplyMessage() {
        return new JoinReplyMessage(this.id, localNodeLng, localNodeLat);
    }

    /**
     * @param message processed message
     * @return true on success || false on fail
     */
    protected boolean processMessage(Message message) {
        if (message instanceof JoinMessage) {
            if (!this.isReplier()) {
                throw new InvalidParameterException("The current handler IS NOT replier, but invalid init JoinMessage received.");
            }
            handleJoinMsg(message);
        }

        if (message instanceof JoinReplyMessage) {
            if (this.isReplier()) {
                throw new InvalidParameterException("The current handler IS replier, but invalid reply JoinReplyMessage received.");
            }
            // todo: In reply message we immediately add the point to the map.
            this.remoteNodeLat = ((JoinReplyMessage) message).getLatitude();
            this.remoteNodeLng = ((JoinReplyMessage) message).getLongitude();

            handleJoinReplyMsg(message);
        }

        // ACK message should be received only by replier (basically the central server).
        // It indicates that our Reply message was received and processed and we now have the contact with the remote node.
        if (message instanceof AckMessage) {
            if (!this.isReplier()) {
                throw new InvalidParameterException("The current handler IS NOT replier, but invalid AckMessage received.");
            }
            handleAckMsg(message);
        }

        // debug
        System.out.println("Received message " + message.getClass().toString()
                + ", lat: " + this.remoteNodeLat + ", lng: " + this.remoteNodeLng);

        return true;
    }

    private void handleJoinMsg(Message msg) {
        // todo: In JoinMessage we remember the coordinates and add them to the map after receiving the ACK message.
        JoinReplyMessage joinReplyMessage = new JoinReplyMessage(id, LocalHop.getInstance().getLongitude(), LocalHop.getInstance().getLatitude());
        joinReplyMessage.setRemoteAddress(msg.getSourceAddress());
        Log.i(TAG, "preparing join reply msg " + joinReplyMessage.toString());
        getQueue().add(joinReplyMessage);
        Log.i(TAG, "added to queue");
    }

    private void handleJoinReplyMsg(Message msg) {
        //todo: send ack
        AckMessage ackMessage = new AckMessage(id);
        ackMessage.setRemoteAddress(msg.getSourceAddress());
        Log.i(TAG, "preparing ack msg " + ackMessage.toString());
        getQueue().add(ackMessage);
        Log.i(TAG, "added to queue");
    }

    private void handleAckMsg(Message msg) {
        Integer newId = this.getNeighbourSetDataHandler().getAllNeighbourSets().size() + 1;

        String nodeAddress = msg.getSourceAddress();
        String dahAddress = msg.getRemoteAddress();

        Location mapPoint = new Location("Berlin");
        mapPoint.setLatitude(this.remoteNodeLat);
        mapPoint.setLongitude(this.remoteNodeLng);

        ELoraNodeState state = ELoraNodeState.UP;

        long currentTime = System.currentTimeMillis();

        NeighbourSet commitSet = new NeighbourSet(newId, nodeAddress, dahAddress, mapPoint, state, currentTime);

        this.getNeighbourSetDataHandler().saveNeighbourSet(commitSet);
    }
}

