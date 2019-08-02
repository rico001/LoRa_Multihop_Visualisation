package de.htwberlin.lora_multihop_logic.components.messages;

import de.htwberlin.lora_multihop_logic.enums.EMessageType;

public class PushMessage extends Message {
    private String hopAddress, attachedHop;
    private double longitude, latitude;

    public PushMessage(String id, String sourceAddress, String remoteAddress, String hopAddress, String attachedHop, Double longitude, Double latitude) {
        super(id, sourceAddress, remoteAddress);

        this.hopAddress = hopAddress;
        this.attachedHop = attachedHop;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    String getType() {
        return EMessageType.PUSH.toString();
    }

    @Override
    String getBody() {
        return hopAddress + "," + attachedHop + "," + latitude + "," + longitude;
    }
}
