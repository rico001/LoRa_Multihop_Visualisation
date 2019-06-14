package de.htwberlin.lora_multihop_logic.components.messages;

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
    private static final String BROADCAST_ADDRESS = "FFFF";
    private static final Integer MESSAGE_SIZE = 18;

    private Double longitude, latitude;

    public JoinMessage(String id, Double lng, Double lat) {
        this.id = id;
        this.longitude = lng;
        this.latitude = lat;
    }

    public JoinMessage(ILoraCommands executor, String sourceAddress, Double longitude, Double latitude) {
        super(executor, sourceAddress, "FFFF");
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
        ILoraCommands loraCommandsExecutor = getExecutor();
        loraCommandsExecutor.setTargetAddress(BROADCAST_ADDRESS);
        loraCommandsExecutor.send(MESSAGE_SIZE);
        loraCommandsExecutor.send(this.toString());
    }
}