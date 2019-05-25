package de.htwberlin.lora_multihop_implementation.components.messages;

import java.util.ArrayList;
import java.util.Arrays;

import de.htwberlin.lora_multihop_implementation.enums.EMessageType;

/**
 * FETCH_REPLY ={SA;N;checksum(N1),checksum(N2),checksum(N),...}
 *
 * SA = Source address
 * N = Amount of entries in NS
 * CHECKSUM = Checksum of a single NS entry
 *
 * Predecessor Message = FETCH
 * Reply Message = ACK
 *
 * Communication Type = UNICAST
 *
 * Use Case: Used to send destination hop NS information
 *
 * @author morelly_t1
 */

public class FetchReplyMessage extends  FetchMessage {

    private  static final EMessageType replyMessage = EMessageType.ACK;
    private  static final boolean UNICAST = Boolean.TRUE;

    private int entries;
    private String[] checksums;

    public FetchReplyMessage(String sourceAddress, int entries, String[] checksums) {
        super(sourceAddress);
        this.entries = entries;
        this.checksums = new String[entries];
    }

    public int getEntries() {
        return entries;
    }

    public void setEntries(int entries) {
        this.entries = entries;
    }


    public String[] getChecksums() {
        return checksums;
    }

    public void setChecksums(String[] checksums) {
        this.checksums = checksums;
    }

    @Override
    public String toString() {
        return "FetchReplyMessage{" +
                "entries=" + entries +
                ", checksums=" + Arrays.toString(checksums) +
                '}';
    }
}
