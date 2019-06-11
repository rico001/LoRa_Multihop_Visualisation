package de.htwberlin.lora_multihop_logic;

import android.util.Log;

import de.htwberlin.lora_multihop_logic.components.lora.IncomingMessageHandler;
import de.htwberlin.lora_multihop_logic.components.lora.LoraHandler;
import de.htwberlin.lora_multihop_visualisation.BluetoothService;
import de.htwberlin.lora_multihop_visualisation.SingletonDevice;
import de.htwberlin.lora_multihop_visualisation.fragments.TerminalFragment;

public class SetupManager {

    private final static String TAG = "lora-setup-manager";

    private TerminalFragment terminalFragment;

    private IncomingMessageHandler incomingMessageHandler;
    private BluetoothService btService;
    private LoraHandler loraHandler;

    public SetupManager(TerminalFragment terminalFragment)   {
        this.terminalFragment = terminalFragment;

        initIncomingMessageHandler();
        initBtService();
        initLoraHandler();
        initIncomingMessageHandlerDestinations();
    }

    private void initIncomingMessageHandler() {
        this.incomingMessageHandler = new IncomingMessageHandler();
    }

    private void initBtService()    {
        try {
            this.btService = new BluetoothService(this.incomingMessageHandler.getMsgHandler(), SingletonDevice.getBluetoothDevice());
            connectBluetooth();
        }   catch (NullPointerException e)  {
            Log.e(TAG, "Choose a device");
        }
    }

    private void initLoraHandler() {
        try {
            this.loraHandler = new LoraHandler(this.btService);
        } catch (NullPointerException e) {
            Log.e(TAG, "Lora Handler could not be init");
        }
    }

    private void initIncomingMessageHandlerDestinations()   {
        this.incomingMessageHandler.setTerminalFragment(this.terminalFragment);
        this.incomingMessageHandler.setLoraHandler(this.loraHandler);
    }

    public void connectBluetooth()  {
        this.btService.connectWithBluetoothDevice();
    }

    public void disconnectBluetooth()   {
        this.btService.disconnect();
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

    public BluetoothService getBtService() {
        return btService;
    }

    public void setBtService(BluetoothService btService) {
        this.btService = btService;
    }

    public IncomingMessageHandler getIncomingMessageHandler() {
        return incomingMessageHandler;
    }

    public void setIncomingMessageHandler(IncomingMessageHandler incomingMessageHandler) {
        this.incomingMessageHandler = incomingMessageHandler;
    }
}
