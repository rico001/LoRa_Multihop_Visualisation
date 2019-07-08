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
import de.htwberlin.lora_multihop_visualisation.SingletonDevice;

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
            handleJoinMsg((JoinMessage) message);

        }

        if (message instanceof JoinReplyMessage) {
            if (this.isReplier()) {
                //throw new InvalidParameterException("The current handler IS replier, but invalid reply JoinReplyMessage received.");
            }
            handleJoinReplyMsg((JoinReplyMessage) message);
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

    private void handleJoinMsg(JoinMessage msg) {
        JoinReplyMessage joinReplyMessage = new JoinReplyMessage(id, LocalHop.getInstance().getLongitude(), LocalHop.getInstance().getLatitude());
        joinReplyMessage.setRemoteAddress(msg.getSourceAddress());

        remoteNodeLng = msg.getLongitude();
        remoteNodeLat = msg.getLatitude();

        Log.i(TAG, "preparing join reply msg " + joinReplyMessage.toString());
        getQueue().add(joinReplyMessage);
        Log.i(TAG, "added to queue");
    }

    private void handleJoinReplyMsg(JoinReplyMessage msg) {
        AckMessage ackMessage = new AckMessage(id);
        ackMessage.setRemoteAddress(msg.getSourceAddress());
        Log.i(TAG, "preparing ack msg " + ackMessage.toString());
        getQueue().add(ackMessage);
        Log.i(TAG, "added to queue");
    }

    private void handleAckMsg(Message msg) {
        Integer newId = this.getNeighbourSetDataHandler().getAllNeighbourSets().size();

        String nodeAddress = msg.getSourceAddress();
        String dahAddress = SingletonDevice.getBluetoothDevice().getAddress().replace(":", "").substring(12 - 4);


        Location mapPoint = new Location("Berlin");

        mapPoint.setLatitude(remoteNodeLat);
        mapPoint.setLongitude(remoteNodeLng);

        NeighbourSet commitSet = new NeighbourSet(newId, nodeAddress, dahAddress, mapPoint, ELoraNodeState.UP, System.currentTimeMillis());

        Log.i(TAG, "adding " + commitSet.toString() + " to Neighbour Set");
        this.getNeighbourSetDataHandler().saveNeighbourSet(commitSet);
    }
}

