package de.htwberlin.lora_multihop_implementation.components.messages;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

/**
 * MOVE ={SA; LAT; LONG;}
 *
 * SA = Source address
 * LAT = Latitude
 * LONG = Longitude
 *
 * Predecessor Message = Null
 * Reply Message = ACK
 *
 *
 * Communication Type = BROADCAST
 *
 * Use Case: Used to inform destination hop for a new position
 *
 * @author morelly_t1
 */

public class MoveMessage extends Message {

    private  static final EMessageType replyMessage = EMessageType.ACK;
    private  static final boolean UNICAST = Boolean.TRUE;

    private double latitude, longitude;


    public MoveMessage(String sourceAddress, double latitude, double longitude) {
        super(sourceAddress);
        this.latitude = latitude;
        this.longitude = longitude;
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
}
