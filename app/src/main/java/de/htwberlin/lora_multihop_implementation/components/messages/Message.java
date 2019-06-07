package de.htwberlin.lora_multihop_implementation.components.messages;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;
import de.htwberlin.lora_multihop_implementation.interfaces.ILoraCommands;

/**
 * Abstract class of a Message
 *
 * @author morelly_t1
 */

public abstract class Message {
    private static final EMessageType REPLY_MESSAGE = null;
    protected String id;
    private String remoteAddress;
    private String sourceAddress;

    private ILoraCommands executor;

    public Message() {

    }

    public Message(ILoraCommands executor, String sourceAddress, String remoteAddress) {
        this.executor = executor;
        this.sourceAddress = sourceAddress;
        this.remoteAddress = remoteAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public EMessageType getAnswerMessage() {
        return REPLY_MESSAGE;
    }

    public ILoraCommands getExecutor() {
        return executor;
    }

    public void setExecutor(ILoraCommands executor) {
        this.executor = executor;
    }

    @Override
    public String toString() {
        return "";
    }

    public abstract void executeAtRoutine(ILoraCommands executor);
}
