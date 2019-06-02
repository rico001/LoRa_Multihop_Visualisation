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
    private String sourceAddress;

    private ILoraCommands executor;

    public Message(String sourceAddress, ILoraCommands executor) {
        this.sourceAddress = sourceAddress;
        this.executor = executor;
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

    public abstract void executeAtRoutine();
}
