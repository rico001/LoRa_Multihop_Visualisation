package de.htwberlin.lora_multihop_implementation.components.messages;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;
import de.htwberlin.lora_multihop_implementation.interfaces.ILoraCommands;

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

    private static final EMessageType REPLY_MESSAGE = EMessageType.ACK;
    private static final Integer MESSAGE_SIZE = 18;

    private String remoteAddress;
    private Double longitude;
    private Double latitude;

    public JoinReplyMessage(String sourceAddress, ILoraCommands executor, String remoteAddress, Double longitude, Double latitude) {
        super(sourceAddress, executor);
        this.remoteAddress = remoteAddress;
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
    public void executeAtRoutine() {

        /*
        AT+DEST=REMOTE_ADDRESS
        AT+SEND=18
        JOIN,LAT,LONG
         */
        ILoraCommands loraCommandsExecutor = super.getExecutor();
        loraCommandsExecutor.setTargetAddress(remoteAddress);
        loraCommandsExecutor.send(MESSAGE_SIZE);
        loraCommandsExecutor.send(this.toString());

    }
}
