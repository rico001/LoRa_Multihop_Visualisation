package de.htwberlin.lora_multihop_implementation.components.messages;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

/**
 * PULL ={SA;demandedAddress(x)}
 *
 * SA = Source address
 * demandedAddress = demandedAddress of a specific single NS entry
 *
 * Predecessor Message = Null
 * Reply Message = PUSH
 *
 * Communication Type = UNICAST
 *
 * Use Case: Used to request a NS entry from a destination hop
 *
 * @author morelly_t1
 */
public class PullMessage extends Message {

    private  static final EMessageType replyMessage = EMessageType.PUSH;
    private  static final boolean UNICAST = Boolean.TRUE;

    private String demandedAddress;

    public PullMessage(String sourceAddress, String demandedAddress) {
        super(sourceAddress);
        this.demandedAddress = demandedAddress;
    }

    public String getDemandedAddress() {
        return demandedAddress;
    }

    public void setDemandedAddress(String demandedAddress) {
        this.demandedAddress = demandedAddress;
    }

    @Override
    public String toString() {
        return "PullMessage{" +
                "demandedAddress='" + demandedAddress + '\'' +
                '}';
    }
}
