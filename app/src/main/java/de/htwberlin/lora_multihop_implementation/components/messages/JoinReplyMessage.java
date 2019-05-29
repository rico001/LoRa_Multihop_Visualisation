package de.htwberlin.lora_multihop_implementation.components.messages;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

/**
 * Length = 18
 * JORP,LAT,LONG
 *
 * LAT = Latitude
 * Long = Longitude
 *
 * Predecessor Message = JOIN
 * Reply Message = ACK
 *
 * Communication Type = UNICAST
 *
 * Use Case: Used to reply to JOIN messages.
 *
 * @author morelly_t1
 */
public class JoinReplyMessage extends Message {

    private  static final EMessageType replyMessage = EMessageType.ACK;
    private  static final boolean UNICAST = Boolean.TRUE;

    private Double longitude;
    private Double latitude;

    public JoinReplyMessage(String sourceAddress, Double longitude, Double latitude) {
        super(sourceAddress);
        this.latitude = latitude;
        this.longitude = longitude;
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

    @Override
    public String toString() {
        return "JoinReplyMessage{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
