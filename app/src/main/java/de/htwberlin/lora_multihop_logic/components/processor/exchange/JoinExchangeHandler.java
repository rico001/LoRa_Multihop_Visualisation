package de.htwberlin.lora_multihop_logic.components.processor.exchange;

import android.location.Location;
import android.util.Log;

import java.security.InvalidParameterException;
import java.util.Queue;

import de.htwberlin.lora_multihop_logic.components.lora.LoraHandler;
import de.htwberlin.lora_multihop_logic.components.messages.AckMessage;
import de.htwberlin.lora_multihop_logic.components.messages.JoinMessage;
import de.htwberlin.lora_multihop_logic.components.messages.JoinReplyMessage;
import de.htwberlin.lora_multihop_logic.components.messages.Message;
import de.htwberlin.lora_multihop_logic.components.model.LocalHop;
import de.htwberlin.lora_multihop_logic.components.model.NeighbourSet;
import de.htwberlin.lora_multihop_logic.components.storage.NeighbourSetTask;
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
    public JoinExchangeHandler(Queue<Message> queue, Double lng, Double lat) {
        super(queue);

        this.localNodeLat = lat;
        this.localNodeLng = lng;
    }

    public JoinExchangeHandler(Queue<Message> queue, Message receivedMessage) {
        super(queue, receivedMessage);
    }

    @Override
    protected Message getStartMessage() {
        return new JoinMessage(this.id, this.sourceAddress, localNodeLng, localNodeLat);
    }

    /**
     * @param message processed message
     * @return true on success || false on fail
     */
    public void processMessage(Message message) {
        if (message instanceof JoinMessage) {
            if (!this.isReplier()) {
                throw new InvalidParameterException("The current handler IS NOT replier, but invalid init JoinMessage received.");
            }
            handleInit(message);
        }

        if (message instanceof JoinReplyMessage) {
            if (this.isReplier()) {
                throw new InvalidParameterException("The current handler IS replier, but invalid reply JoinReplyMessage received.");
            }
            // todo: In reply message we immediately add the point to the map.
            this.remoteNodeLat = ((JoinReplyMessage) message).getLatitude();
            this.remoteNodeLng = ((JoinReplyMessage) message).getLongitude();

            handleReply(message);
        }

        // ACK message should be received only by replier (basically the central server).
        // It indicates that our Reply message was received and processed and we now have the contact with the remote node.
        if (message instanceof AckMessage) {
            if (!this.isReplier()) {
                throw new InvalidParameterException("The current handler IS NOT replier, but invalid AckMessage received.");
            }
            handleAck((AckMessage) message);
        }

        // debug
        System.out.println("Received message " + message.getClass().toString()
                + ", lat: " + this.remoteNodeLat + ", lng: " + this.remoteNodeLng);
    }

    @Override
    protected void handleInit(Message msg) {
        // todo: In JoinMessage we remember the coordinates and add them to the map after receiving the ACK message.
        JoinReplyMessage joinReplyMessage = new JoinReplyMessage(
                id,
                this.sourceAddress,
                msg.getSourceAddress(),
                LocalHop.getInstance().getLongitude(),
                LocalHop.getInstance().getLatitude()
        );
        Log.i(TAG, "preparing join reply msg " + joinReplyMessage.toString());
        getQueue().add(joinReplyMessage);
        Log.i(TAG, "added to queue");
    }

    @Override
    protected void handleReply(Message msg) {
        AckMessage ackMessage = new AckMessage(id, LoraHandler.getSourceAddress(), msg.getSourceAddress());
        Log.i(TAG, "preparing ack msg " + ackMessage.toString());
        getQueue().add(ackMessage);
        Log.i(TAG, "added to queue");
    }

    @Override
    protected void handleAck(AckMessage msg) {
        // todo: add the remoteLat, remoteLng (remembered from the JoinMessage) to the map.
        // todo: add to NS and table fragment
        Location gps = new Location("remote-node");
        gps.setLongitude(50.212);
        gps.setLatitude(40.121);

        NeighbourSet entry = new NeighbourSet(
                1,
                msg.getRemoteAddress(),
                msg.getSourceAddress(),
                gps,
                ELoraNodeState.UP,
                System.currentTimeMillis()
        );
        NeighbourSetTask nst = new NeighbourSetTask();
        nst.execute(entry);
    }

}