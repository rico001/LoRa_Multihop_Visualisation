package de.htwberlin.lora_multihop_logic;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import de.htwberlin.lora_multihop_logic.components.lora.IncomingMessageHandler;
import de.htwberlin.lora_multihop_logic.components.lora.LoraHandler;
import de.htwberlin.lora_multihop_logic.components.model.NeighbourSet;
import de.htwberlin.lora_multihop_logic.enums.ELoraNodeState;
import de.htwberlin.lora_multihop_visualisation.BluetoothService;
import de.htwberlin.lora_multihop_visualisation.SingletonDevice;
import de.htwberlin.lora_multihop_visualisation.fragments.MapFragment;
import de.htwberlin.lora_multihop_visualisation.fragments.TerminalFragment;

public class SetupManager   {

    private final static String TAG = "lora-setup-manager";

    private Context context;

    private TerminalFragment terminalFragment;
    private MapFragment mapFragment;

    private IncomingMessageHandler incomingMessageHandler;
    private BluetoothService btService;
    private LoraHandler loraHandler;

    public SetupManager(Context context, MapFragment mapFragment, TerminalFragment terminalFragment)   {
        this.context = context;
        this.mapFragment = mapFragment;
        this.terminalFragment = terminalFragment;

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

    // TODO:    JUST A SKETCH FOR TESTING
    //          also remove method from constructor
    private void initNeighboursetData() {
        NeighbourSetData nsd = new NeighbourSetData(this.context);

        // Testing
        Location mapPoint = new Location("NodeOne");
        mapPoint.setLatitude(52.463201);
        mapPoint.setLongitude(13.507464);

        NeighbourSet nsOne = new NeighbourSet(420, "AAAA", "BBBB", mapPoint, ELoraNodeState.UP, System.currentTimeMillis());

        nsd.saveNeighbourSet(nsOne);
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

    public MapFragment getMapFragment() {
        return mapFragment;
    }

    public void setMapFragment(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
