package de.htwberlin.lora_multihop_implementation.components.messages;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

/**
 * @author morelly_t1
 */

public class Message {

    private EMessageType type;
    private String sourceAddress;

    public Message(EMessageType type, String sourceAddress) {
        this.type = type;
        this.sourceAddress = sourceAddress;
    }

    public EMessageType getType() {
        return type;
    }

    public void setType(EMessageType type) {
        this.type = type;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }
}
