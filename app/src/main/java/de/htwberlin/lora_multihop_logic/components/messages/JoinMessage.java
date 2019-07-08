package de.htwberlin.lora_multihop_logic.components.messages;

import android.util.Log;

import de.htwberlin.lora_multihop_logic.enums.EMessageType;
import de.htwberlin.lora_multihop_logic.interfaces.ILoraCommands;

/**
 * BYTE LENGTH  = 18
 * <p>
 * JOIN;LAT;LONG
 * <p>
 * LAT = Latitude
 * Long = Longitude
 * <p>
 * Predecessor Message = Null
 * Reply Message = JOIN_REPLY
 * <p>
 * Communication Type = BROADCAST
 * <p>
 * Use Case: Used to detect possible destination hops.
 *
 * @author morelly_t1
 */
public class JoinMessage extends Message {

    private static final String TAG = "JoinMessage";
    private static final String BROADCAST_ADDRESS = "FFFF";

    private Double longitude, latitude;

    public JoinMessage(String id, Double lng, Double lat) {
        this.id = id;
        this.longitude = lng;
        this.latitude = lat;
        this.setRemoteAddress(BROADCAST_ADDRESS);
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public JoinMessage(ILoraCommands executor, String sourceAddress, Double latitude, Double longitude) {
        super(executor, sourceAddress);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return EMessageType.JOIN + "," + latitude + "," + longitude;
    }


    public void executeAtRoutine(ILoraCommands executor) {
        /*
        AT+DEST=FFFF
        AT+SEND=18
        JOIN,LAT,LONG
         */
        ILoraCommands loraCommandsExecutor = super.getExecutor();

        try {
            loraCommandsExecutor.setTargetAddress(BROADCAST_ADDRESS);
            Thread.sleep(1000);
            loraCommandsExecutor.send(this.toString().length());
            Thread.sleep(1000);
            loraCommandsExecutor.send(this.toString());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Log.e(TAG, "exception in while sending join: " + e.getMessage());
        }
    }
}
