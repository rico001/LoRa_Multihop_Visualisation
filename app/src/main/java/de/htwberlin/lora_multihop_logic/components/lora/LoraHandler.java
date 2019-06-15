package de.htwberlin.lora_multihop_logic.components.lora;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import de.htwberlin.lora_multihop_logic.components.model.LocalHop;
import de.htwberlin.lora_multihop_logic.components.parser.MessageParser;
import de.htwberlin.lora_multihop_logic.components.parser.ParserException;
import de.htwberlin.lora_multihop_logic.components.processor.MessageProcessor;
import de.htwberlin.lora_multihop_logic.interfaces.ILoraCommands;
import de.htwberlin.lora_multihop_logic.interfaces.MessageConstants;
import de.htwberlin.lora_multihop_visualisation.BluetoothService;
import de.htwberlin.lora_multihop_visualisation.SingletonDevice;
import de.htwberlin.lora_multihop_visualisation.fragments.MapFragment;

public class LoraHandler extends AppCompatActivity implements MessageConstants {

    private static final String TAG = "MessageHandler";

    private BluetoothService btService;
    private MapFragment mapFragment;

    private ILoraCommands executor;
    private MessageParser parser;
    private MessageProcessor processor;

    public static LoraHandler instance = null;

    public LoraHandler(BluetoothService btService, MapFragment mapFragment) {
        this.btService = btService;
        this.mapFragment = mapFragment;

        this.executor = new LoraCommandsExecutor(this.btService);
        this.parser = new MessageParser(this.executor);
        this.processor = new MessageProcessor(this.executor);
    }

    public void processLoraResponse(String responseMsg) {
        if (parseMessage(responseMsg)) processMessage();
    }

    private boolean parseMessage(String message) {
        try {
            initLocalHop(); //TODO: not the best place, but works
            Log.i(TAG, "parsing message " + message);
            if (message.startsWith("LR,")) {
                parser.parseInput(message);
                Log.i(TAG, "parsed message");
                return Boolean.TRUE;
            }
        } catch (ParserException | ArrayIndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return Boolean.FALSE;
        }

        Log.i(TAG, "unable message");
        return Boolean.FALSE;
    }

    private void processMessage() {
        processor.processMessage();
    }

    private void initLocalHop() {
        LocalHop localHop = LocalHop.getInstance();
        LatLng location = mapFragment.getLocation();

        String deviceAddress = SingletonDevice.getBluetoothDevice()
                .getAddress()
                .replace(":", "")
                .substring(12 - 4);
        localHop.setAddress(deviceAddress);
        localHop.setLatitude(location.latitude);
        localHop.setLongitude(location.longitude);
    }

    public static LoraHandler getInstance() {
        return instance;
    }

    public static LoraHandler getInstance(BluetoothService btService, MapFragment mapFragment) {
        if (instance == null) instance = new LoraHandler(btService, mapFragment);
        return instance;
    }

    public MessageParser getParser() {
        return parser;
    }

    public void setParser(MessageParser parser) {
        this.parser = parser;
    }

    public ILoraCommands getExecutor() {
        return executor;
    }

    public void setExecutor(ILoraCommands executor) {
        this.executor = executor;
    }

    public MessageProcessor getProcessor() {
        return processor;
    }

    public void setProcessor(MessageProcessor processor) {
        this.processor = processor;
    }

    public BluetoothService getBtService() {
        return btService;
    }

    public void setBtService(BluetoothService btService) {
        this.btService = btService;
    }
}
