package de.htwberlin.lora_multihop_implementation.components.lora;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;


import de.htwberlin.lora_multihop_implementation.interfaces.MessageConstants;
import de.htwberlin.lora_multihop_visualisation.SingletonDevice;
import de.htwberlin.lora_multihop_visualisation.fragments.TerminalFragment;

public class IncomingMessageHandler implements MessageConstants {

    private final static int sendColor = Color.RED;
    private final static int readColor = Color.BLUE;

    private TerminalFragment terminalFragment;
    private LoraHandler loraHandler;

    private final Handler msgHandler = new Handler() {
        @Override
        public synchronized void handleMessage(Message msg) {
            switch (msg.what) {
                case STATE_CONNECTING:
                    terminalFragment.updateTerminalMessages(readColor, "Verbindung mit " + SingletonDevice.getBluetoothDevice().getName() + " wird aufgebaut", false);
                    break;
                case STATE_CONNECTED:
                    terminalFragment.updateTerminalMessages(readColor, "Verbindung ist aufgebaut", false);
                    break;
                case MESSAGE_READ:
                    String message = (String) msg.obj;
                    terminalFragment.updateTerminalMessages(readColor, message, false);
                    // update all fragements gui
                    // pass to handler for logic stuff
                    loraHandler.processLoraResponse(message);
                    break;
                case MESSAGE_ERROR:
                    System.out.println("MSG ERROR");
                    break;
            }
        }
    };

    public IncomingMessageHandler() {

    }

    public IncomingMessageHandler(LoraHandler loraHandler, TerminalFragment terminalFragment) {
        this.loraHandler = loraHandler;
        this.terminalFragment = terminalFragment;
    }

    public TerminalFragment getTerminalFragment() {
        return terminalFragment;
    }

    public void setTerminalFragment(TerminalFragment terminalFragment) {
        this.terminalFragment = terminalFragment;
    }

    public LoraHandler getLoraHandler() {
        return loraHandler;
    }

    public void setLoraHandler(LoraHandler loraHandler) {
        this.loraHandler = loraHandler;
    }

    public Handler getMsgHandler() {
        return msgHandler;
    }

    public static int getSendColor() {
        return sendColor;
    }

    public static int getReadColor() {
        return readColor;
    }
}
