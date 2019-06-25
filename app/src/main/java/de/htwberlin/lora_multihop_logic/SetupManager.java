package de.htwberlin.lora_multihop_logic;

import android.location.Location;
import android.util.Log;

import de.htwberlin.lora_multihop_logic.components.lora.IncomingMessageHandler;
import de.htwberlin.lora_multihop_logic.components.lora.LoraHandler;
import de.htwberlin.lora_multihop_logic.components.model.NeighbourSet;
import de.htwberlin.lora_multihop_logic.enums.ELoraNodeState;
import de.htwberlin.lora_multihop_visualisation.BluetoothService;
import de.htwberlin.lora_multihop_visualisation.SingletonDevice;
import de.htwberlin.lora_multihop_visualisation.fragments.MapFragment;
import de.htwberlin.lora_multihop_visualisation.fragments.NeighbourSetTableFragment;
import de.htwberlin.lora_multihop_visualisation.fragments.TerminalFragment;

public class SetupManager   {

    private final static String TAG = "lora-setup-manager";

    private TerminalFragment terminalFragment;
    private MapFragment mapFragment;
    private NeighbourSetTableFragment nstFragment;

    private NeighbourSetData neighbourSetData;

    private IncomingMessageHandler incomingMessageHandler;
    private BluetoothService btService;
    private LoraHandler loraHandler;

    public SetupManager(MapFragment mapFragment, TerminalFragment terminalFragment, NeighbourSetTableFragment nstFragment)   {
        this.mapFragment = mapFragment;
        this.terminalFragment = terminalFragment;
        this.nstFragment = nstFragment;

        initIncomingMessageHandler();
        initBtService();
        initLoraHandler();
        initIncomingMessageHandlerDestinations();

        initNeighboursetData();
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
            this.loraHandler = new LoraHandler(this.btService, this.mapFragment);
        } catch (NullPointerException e) {
            Log.e(TAG, "Lora Handler could not be init");
        }
    }

    private void initNeighboursetData() {
        this.neighbourSetData = new NeighbourSetData(this.nstFragment, this.mapFragment);

        this.neighbourSetData.saveNeighbourSet(createTestNeighbourSet());
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

    public LoraHandler getLoraHandler() {
        return loraHandler;
    }

    public BluetoothService getBtService() {
        return btService;
    }

    public NeighbourSetData getNeighbourSetData() {
        return neighbourSetData;
    }

    private NeighbourSet createTestNeighbourSet()   {
        Location mapPoint = new Location("Berlin");
        mapPoint.setLatitude(52.520007);
        mapPoint.setLongitude(13.404954);
        return new NeighbourSet(420, "AAAB", "BBBA", mapPoint, ELoraNodeState.PENDING, System.currentTimeMillis());
    }
}
