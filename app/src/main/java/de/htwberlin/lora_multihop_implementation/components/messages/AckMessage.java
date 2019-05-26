package de.htwberlin.lora_multihop_implementation.components.messages;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

/**
 * ACK ={SA}
 *
 * SA = Source address
 *
 * Predecessor Message = JOIN_REPLY, FETCH_REPLY, MOVE, PUSH
 * Reply Message = ACK
 *
 *
 * Communication Type = UNICAST
 *
 * Use Case: Used to confirm received messages
 * Results in updating an NS entry or adding an NS entry
 *
 * @author morelly_t1
 */
public class AckMessage extends Message {

    private  static final EMessageType replyMessage = null;
    private  static final boolean UNICAST = Boolean.TRUE;

    public AckMessage(String sourceAddress) {
        super(sourceAddress);
    }

}
