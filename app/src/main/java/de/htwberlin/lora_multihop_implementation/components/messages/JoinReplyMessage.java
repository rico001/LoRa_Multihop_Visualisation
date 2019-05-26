package de.htwberlin.lora_multihop_implementation.components.messages;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

/**
 * JOIN_REPLY ={SA, LAT, LONG}
 *
 * SA = Source Address
 * LAT = Latitude
 * Long = Longitude
 *
 * Predecessor Message = JOIN
 * Reply Message = ACK
 *
 * Communication Type = UNICAST
 *
 * Use Case: Used to reply to JOIN messages.
 *
 * @author morelly_t1
 */
public class JoinReplyMessage extends JoinMessage {

    private  static final EMessageType replyMessage = EMessageType.ACK;
    private  static final boolean UNICAST = Boolean.TRUE;


    public JoinReplyMessage(String sourceAddress, Double longitude, Double latitude) {
        super(sourceAddress, longitude, latitude);
    }


}
