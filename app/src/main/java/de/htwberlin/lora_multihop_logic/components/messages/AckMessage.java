package de.htwberlin.lora_multihop_logic.components.messages;

import de.htwberlin.lora_multihop_logic.enums.EMessageType;
import de.htwberlin.lora_multihop_logic.interfaces.ILoraCommands;

public class AckMessage extends Message {

    private static final String TAG = "AckMessage";

    public AckMessage(String id) {
        this.id = id;
    }

    public AckMessage(ILoraCommands executor, String sourceAddress) {
        super(executor, sourceAddress);
    }

    @Override
    public String toString() {
        return EMessageType.ACK.toString();
    }

    @Override
    public void executeAtRoutine(ILoraCommands executor) {

        /*
        AT+DEST=REMOTE_ADDRESS
        AT+SEND=3
        ACK
         */

        ILoraCommands loraCommandsExecutor = super.getExecutor();

        try {
            loraCommandsExecutor.setTargetAddress(getRemoteAddress());
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
