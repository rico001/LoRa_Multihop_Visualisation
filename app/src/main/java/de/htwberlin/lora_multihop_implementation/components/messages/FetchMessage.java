package de.htwberlin.lora_multihop_implementation.components.messages;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

/**
 * FETCH ={SA}
 *
 * SA = Source Address
 *
 * Reply Message = FETCH_REPLY
 * Communication Type = UNICAST
 *
 * Use Case: Used to fetch Neighbour Set entriys from destination hop.
 *
 * @author morelly_t1
 */

public class FetchMessage extends Message {

    private  static final EMessageType replyMessage = EMessageType.FETCH_REPLY;
    private  static final boolean UNICAST = Boolean.TRUE;

    public FetchMessage(String sourceAddress) {
        super( sourceAddress);
    }


}
