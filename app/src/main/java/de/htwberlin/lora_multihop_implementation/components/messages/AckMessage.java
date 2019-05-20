package de.htwberlin.lora_multihop_implementation.components.messages;

/**
 * @author morelly_t1
 */
public class AckMessage extends Message {

    public AckMessage(EnumMessageType type, String sourceAddress) {
        super(type, sourceAddress);
    }
}
