package de.htwberlin.lora_multihop_logic.components.messages;

import de.htwberlin.lora_multihop_logic.enums.EMessageType;

public class FetchReplyMessage extends Message {
    private int neighborsCount;
    private String[] checksums;

    public FetchReplyMessage(String id, String sourceAddress, String remoteAddress, int neighborsCount, String[] checksums) {
        super(id, sourceAddress, remoteAddress);

        this.neighborsCount = neighborsCount;
        this.checksums = checksums;
    }

    @Override
    String getType() {
        return EMessageType.FETCH_REPLY.toString();
    }

    @Override
    String getBody() {
        // todo: should it be "count,... n checksums of neighbors"?
        return neighborsCount + "," + String.join(",", checksums);
    }
}
