package de.htwberlin.lora_multihop_logic.components.messages;

import de.htwberlin.lora_multihop_logic.enums.EMessageType;

public class PullMessage extends Message {
    private String nsChecksum;

    public PullMessage(String id, String sourceAddress, String remoteAddress, String nsChecksum) {
        super(id, sourceAddress, remoteAddress);
        this.nsChecksum = nsChecksum;
    }

    @Override
    String getType() {
        return EMessageType.PULL.toString();
    }

    @Override
    String getBody() {
        return nsChecksum;
    }
}
