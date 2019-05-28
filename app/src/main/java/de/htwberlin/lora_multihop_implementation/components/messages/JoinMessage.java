package de.htwberlin.lora_multihop_implementation.components.messages;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

/**
 * JOIN={SA, LAT, LONG}
 *
 * SA = Source Address
 * LAT = Latitude
 * Long = Longitude
 *
 * Predecessor Message = Null
 * Reply Message = JOIN_REPLY
 *
 * Communication Type = BROADCAST
 *
 * Use Case: Used to detect possible destination hops.
 *
 * @author morelly_t1
 */
public class JoinMessage extends Message {

    private  static final EMessageType replyMessage = EMessageType.JOIN_REPLY;
    private  static final boolean UNICAST = Boolean.FALSE;

    private Double longitude, latitude;

    public JoinMessage(String sourceAddress, Double longitude, Double latitude) {
        super(sourceAddress);
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


    public Message getResponse(String source, Double lng, Double lat) {
        return new JoinReplyMessage(source, lng, lat);
    }

    @Override
    public String toString() {
        return "JoinMessage{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

}
