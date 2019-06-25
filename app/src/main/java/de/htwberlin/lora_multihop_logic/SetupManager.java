package de.htwberlin.lora_multihop_logic;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import de.htwberlin.lora_multihop_logic.components.lora.IncomingMessageHandler;
import de.htwberlin.lora_multihop_logic.components.lora.LoraHandler;
import de.htwberlin.lora_multihop_logic.components.model.LocalHop;
import de.htwberlin.lora_multihop_logic.components.model.NeighbourSet;
import de.htwberlin.lora_multihop_logic.enums.ELoraNodeState;
import de.htwberlin.lora_multihop_visualisation.BluetoothService;
import de.htwberlin.lora_multihop_visualisation.SingletonDevice;
import de.htwberlin.lora_multihop_visualisation.fragments.MapFragment;
import de.htwberlin.lora_multihop_visualisation.fragments.NeighbourSetTableFragment;
import de.htwberlin.lora_multihop_visualisation.fragments.TerminalFragment;

public class SetupManager   {

    private final static String TAG = "lora-setup-manager";

    public final  static Integer LOCAL_HOP_ID = 0;

    private TerminalFragment terminalFragment;
    private MapFragment mapFragment;
    private NeighbourSetTableFragment nstFragment;

    private NeighbourSetDataHandler neighbourSetDataHandler;

    private IncomingMessageHandler incomingMessageHandler;
    private BluetoothService btService;
    private LoraHandler loraHandler;

    public SetupManager(MapFragment mapFragment, TerminalFragment terminalFragment, NeighbourSetTableFragment nstFragment)   {
        this.mapFragment = mapFragment;
        this.terminalFragment = terminalFragment;
        this.nstFragment = nstFragment;

        initNeighboursetData();

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
            this.loraHandler = new LoraHandler(this.btService, this.neighbourSetDataHandler);
        } catch (NullPointerException e) {
            Log.e(TAG, "Lora Handler could not be init");
        }
    }

    private void initNeighboursetData() {
        this.neighbourSetDataHandler = new NeighbourSetDataHandler(this.nstFragment, this.mapFragment);
        this.neighbourSetDataHandler.saveNeighbourSet(createLocalHopNeighbourSet());
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

    public NeighbourSetDataHandler getNeighbourSetDataHandler() {
        return neighbourSetDataHandler;
    }

    private NeighbourSet createLocalHopNeighbourSet()   {
        LocalHop localHop = LocalHop.getInstance();

        localHop.setId(LOCAL_HOP_ID);

        /* TODO commented out for emulator
        String deviceAddress = SingletonDevice.getBluetoothDevice()

                .getAddress()
                .replace(":", "")
                .substring(12 - 4);
        */
        String deviceAddress = "AAAA";
        localHop.setAddress(deviceAddress);

        // TODO map fragment fix .getLocation()
        //LatLng location = mapFragment.getLocation();
        //localHop.setLatitude(location.latitude);
        //localHop.setLongitude(location.longitude);
        localHop.setLatitude(52.457339);
        localHop.setLongitude(13.526851);

        Location mapPoint = new Location("LocalHop");
        mapPoint.setLatitude(localHop.getLatitude());
        mapPoint.setLongitude(localHop.getLongitude());

        return new NeighbourSet(localHop.getId(), localHop.getAddress(), "ABBA", mapPoint, ELoraNodeState.UP, System.currentTimeMillis());
    }
}
