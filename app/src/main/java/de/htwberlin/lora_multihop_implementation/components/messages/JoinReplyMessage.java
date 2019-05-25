package de.htwberlin.lora_multihop_implementation.components.messages;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

/**
 * @author morelly_t1
 */
public class JoinReplyMessage extends JoinMessage {

    public JoinReplyMessage(EMessageType type, String sourceAddress, Double longitude, Double latitude) {
        super(type, sourceAddress, longitude, latitude);
    }
}
