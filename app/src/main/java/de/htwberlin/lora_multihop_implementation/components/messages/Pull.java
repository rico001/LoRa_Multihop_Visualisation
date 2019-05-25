package de.htwberlin.lora_multihop_implementation.components.messages;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

/**
 * PULL ={SA;checksum(x)}
 *
 * SA = Source address
 * CHECKSUM = Checksum of a specific single NS entry
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
public class Pull extends Message {

    private  static final EMessageType replyMessage = EMessageType.PUSH;
    private  static final boolean UNICAST = Boolean.TRUE;

    private String checksum;

    public Pull(String sourceAddress, String checksum) {
        super(sourceAddress);
        this.checksum = checksum;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    @Override
    public String toString() {
        return "Pull{" +
                "checksum='" + checksum + '\'' +
                '}';
    }
}
