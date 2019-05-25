package de.htwberlin.lora_multihop_implementation.components.messages;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

public class MoveMessage extends Message {

    public MoveMessage(EMessageType type, String sourceAddress) {
        super(type, sourceAddress);
    }
}
