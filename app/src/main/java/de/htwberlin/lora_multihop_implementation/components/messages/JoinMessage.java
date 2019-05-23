package de.htwberlin.lora_multihop_implementation.components.messages;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

/**
 * @author morelly_t1
 */
public class JoinMessage extends Message {

    private Double longitude, latitude;

    public JoinMessage(EMessageType type, String sourceAddress, Double longitude, Double latitude) {
        super(type, sourceAddress);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
