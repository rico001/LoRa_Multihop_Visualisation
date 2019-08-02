package de.htwberlin.lora_multihop_logic.components.messages;

import de.htwberlin.lora_multihop_logic.enums.EMessageType;

public class FetchMessage extends Message {
    public FetchMessage(String id, String sourceAddress, String remoteAddress) {
        super(id, sourceAddress, remoteAddress);
    }

    @Override
    String getType() {
        return EMessageType.FETCH.toString();
    }

    @Override
    String getBody() {
        return null;
    }
}
