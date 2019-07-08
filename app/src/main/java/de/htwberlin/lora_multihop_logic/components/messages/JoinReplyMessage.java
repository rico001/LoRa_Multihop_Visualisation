package de.htwberlin.lora_multihop_logic.components.messages;

import de.htwberlin.lora_multihop_logic.enums.EMessageType;
import de.htwberlin.lora_multihop_logic.interfaces.ILoraCommands;

/**
 * Length = 18
 * JORP,LAT,LONG
 * <p>
 * LAT = Latitude
 * Long = Longitude
 * <p>
 * Predecessor Message = JOIN
 * Reply Message = ACK
 * <p>
 * Communication Type = UNICAST
 * <p>
 * Use Case: Used to reply to JOIN messages.
 *
 * @author morelly_t1
 */
public class JoinReplyMessage extends Message {

    private static final String TAG = "JoinReplyMessage";

    private Double longitude;
    private Double latitude;

    public JoinReplyMessage(String id, Double lng, Double lat) {
        this.id = id;
        this.longitude = lng;
        this.latitude = lat;
    }

    public JoinReplyMessage(ILoraCommands executor, String sourceAddress, Double longitude, Double latitude) {
        super(executor, sourceAddress);
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
        return EMessageType.JORP + "," + latitude + "," + longitude;
    }

    @Override
    public void executeAtRoutine(ILoraCommands executor) {

        /*
        AT+DEST=REMOTE_ADDRESS
        AT+SEND=18
        JOIN,LAT,LONG
         */
        ILoraCommands loraCommandsExecutor = super.getExecutor();

        try {
            loraCommandsExecutor.setTargetAddress(super.getRemoteAddress());
            Thread.sleep(1000);
            loraCommandsExecutor.send(this.toString().length());
            Thread.sleep(1000);
            loraCommandsExecutor.send(this.toString());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
