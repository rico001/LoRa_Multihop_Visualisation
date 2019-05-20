package de.htwberlin.lora_multihop_implementation.components.messages;

/**
 * @author morelly_t1
 */
public class JoinReplyMessage extends JoinMessage {

    public JoinReplyMessage(EnumMessageType type, String sourceAddress, Double longitude, Double latitude) {
        super(type, sourceAddress, longitude, latitude);
    }
}
