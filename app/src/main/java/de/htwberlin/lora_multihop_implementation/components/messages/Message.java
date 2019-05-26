package de.htwberlin.lora_multihop_implementation.components.messages;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

/**
 * Abstract class of a Message
 * @author morelly_t1
 */

public abstract class Message {

    private EMessageType type;
    private String sourceAddress;
    private EMessageType replyMessage;

    public Message(String sourceAddress) {
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

    public EMessageType getAnswerMessage() {
        return replyMessage;
    }

    public void setAnswerMessage(EMessageType replyMessage) {
        this.replyMessage = replyMessage;
    }


    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", sourceAddress='" + sourceAddress + '\'' +
                ", replyMessage=" + replyMessage +
                '}';
    }


}
