package de.htwberlin.lora_multihop_logic.components.messages;

import de.htwberlin.lora_multihop_logic.enums.EMessageType;
import de.htwberlin.lora_multihop_logic.interfaces.ILoraCommands;

public class AckMessage extends Message {

    private static final String TAG = "AckMessage";
    private static final Integer MESSAGE_SIZE = 3;

    public AckMessage(String id) {
        this.id = id;
    }

    public AckMessage(ILoraCommands executor, String sourceAddress, String remoteAddres) {
        super(executor, sourceAddress, remoteAddres);
    }

    @Override
    public String toString() {
        return EMessageType.ACK.toString();
    }

    @Override
    public void executeAtRoutine(ILoraCommands executor) {
        /*
        AT+DEST=REMOTE_ADDRESS
        AT+SEND=18
        ACK,LAT,LONG
         */
        ILoraCommands loraCommandsExecutor = super.getExecutor();

        try {
            loraCommandsExecutor.setTargetAddress(super.getRemoteAddress());
            Thread.sleep(1000);
            loraCommandsExecutor.send(MESSAGE_SIZE);
            Thread.sleep(1000);
            loraCommandsExecutor.send(this.toString());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
