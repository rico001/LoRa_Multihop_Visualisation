package de.htwberlin.lora_multihop_implementation.components.messages;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

/**
 * LEAVE ={SA}
 *
 * SA = Source address
 *
 * Predecessor Message = Null
 * Reply Message = Null
 *
 * Communication Type = BROADCAST
 *
 * Use Case: Used to inform hops that a node leaves the ad hoc network
 *
 * Results in updating a NS entry
 *
 * @author morelly_t1
 */

public class LeaveMessage extends Message {

    private  static final EMessageType replyMessage = null;
    private  static final boolean UNICAST = Boolean.FALSE;

    public LeaveMessage(String sourceAddress) {
        super(sourceAddress);
    }

    protected void updateNSEntry(){

    }
}
