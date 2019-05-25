package de.htwberlin.lora_multihop_implementation.components.messages;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

/**
 * PUSH ={SA;SAH; DAH; LAT; LONG;}
 *
 * SA = Source address
 * SAH = Source Address of Hop
 * DAH = Direct attached Hop
 * LAT = Latitude
 * LONG = Longitude
 *
 * Predecessor Message = Null
 * Reply Message = ACK
 *
 * Communication Type = UNICAST
 *
 * Use Case: Used to send a NS entry to a destination hop
 *
 * @author morelly_t1
 */

public class Push extends Message {

    private  static final EMessageType replyMessage = EMessageType.ACK;
    private  static final boolean UNICAST = Boolean.TRUE;

    private String sourceAddressOfHop;
    private String directAttachedHop;
    private double latitude, longitude;

    public Push(String sourceAddress, String sourceAddressOfHop, String directAttachedHop, double latitude, double longitude) {
        super(sourceAddress);
        this.sourceAddressOfHop = sourceAddressOfHop;
        this.directAttachedHop = directAttachedHop;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getSourceAddressOfHop() {
        return sourceAddressOfHop;
    }

    public void setSourceAddressOfHop(String sourceAddressOfHop) {
        this.sourceAddressOfHop = sourceAddressOfHop;
    }

    public String getDirectAttachedHop() {
        return directAttachedHop;
    }

    public void setDirectAttachedHop(String directAttachedHop) {
        this.directAttachedHop = directAttachedHop;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Push{" +
                "sourceAddressOfHop='" + sourceAddressOfHop + '\'' +
                ", directAttachedHop='" + directAttachedHop + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}


