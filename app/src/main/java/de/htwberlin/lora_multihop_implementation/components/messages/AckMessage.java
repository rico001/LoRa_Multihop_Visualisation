package de.htwberlin.lora_multihop_implementation.components.messages;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

/**
 * @author morelly_t1
 */
public class AckMessage extends Message {

    public AckMessage(EMessageType type, String sourceAddress) {
        super(type, sourceAddress);
    }
}
