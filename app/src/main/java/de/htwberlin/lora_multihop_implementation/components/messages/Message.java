package de.htwberlin.lora_multihop_implementation.components.messages;

/**
 * @author morelly_t1
 */

public class Message {

    private EnumMessageType type;
    private String sourceAddress;

    public Message(EnumMessageType type, String sourceAddress) {
        this.type = type;
        this.sourceAddress = sourceAddress;
    }

    public EnumMessageType getType() {
        return type;
    }

    public void setType(EnumMessageType type) {
        this.type = type;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }
}
