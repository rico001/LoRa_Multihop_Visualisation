package de.htwberlin.lora_multihop_implementation.components.processor;

import java.security.InvalidParameterException;
import java.util.Queue;

import de.htwberlin.lora_multihop_implementation.components.messages.AckMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.JoinMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.JoinReplyMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.Message;

public class JoinExchangeHandler extends ExchangeHandler {
    private Double lng;
    private Double lat;

    /**
     * Generates a new init message and enqueues it.
     * Should be invoked as a result of the user-triggered event "Want to connect" (is it a button? idk).
     */
    JoinExchangeHandler(Queue<Message> queue, Double lng, Double lat) {
        super(queue);

        this.lat = lat;
        this.lng = lng;
    }

    JoinExchangeHandler(Queue<Message> queue, Message receivedMessage) {
        super(queue, receivedMessage);
    }

    @Override
    protected Message getStartMessage() {
        return new JoinMessage(this.id, lng, lat);
    }

    @Override
    protected Message getReplyMessage() {
        return new JoinReplyMessage(this.id, lng, lat);
    }

    protected boolean processMessage(Message message) {
        // todo: probably need to store in instance so it can be accessed when received ACK message.
        Double remoteLat = null, remoteLng = null;

        if (message instanceof JoinMessage) {
            if (!this.isReplier()) {
                throw new InvalidParameterException("The current handler IS NOT replier, but invalid init JoinMessage received.");
            }

            // todo: In JoinMessage we remember the coordinates and add them to the map after receiving the ACK message.
            remoteLat = ((JoinMessage) message).getLatitude();
            remoteLng = ((JoinMessage) message).getLongitude();
        }
        if (message instanceof JoinReplyMessage) {
            if (this.isReplier()) {
                throw new InvalidParameterException("The current handler IS replier, but invalid reply JoinReplyMessage received.");
            }

            // todo: In reply message we immediately add the point to the map.
            remoteLat = ((JoinReplyMessage) message).getLatitude();
            remoteLng = ((JoinReplyMessage) message).getLongitude();
        }

        // ACK message should be received only by replier (basically the central server).
        // It indicates that our Reply message was received and processed and we now have the contact with the remote node.
        if (message instanceof AckMessage) {
            if (!this.isReplier()) {
                throw new InvalidParameterException("The current handler IS NOT replier, but invalid AckMessage received.");
            }

            // todo: add the remoteLat, remoteLng (remembered from the JoinMessage) to the map.
        }


        // debug
        System.out.println("Received message " + message.getClass().toString()
                + ", lat: " + remoteLat + ", lng: " + remoteLng);

        return true;
    }
}
