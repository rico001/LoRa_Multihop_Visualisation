package de.htwberlin.lora_multihop_implementation.components.lora;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import de.htwberlin.lora_multihop_implementation.components.model.LocalHop;
import de.htwberlin.lora_multihop_implementation.components.parser.MessageParser;
import de.htwberlin.lora_multihop_implementation.components.parser.ParserException;
import de.htwberlin.lora_multihop_implementation.components.processor.MessageProcessor;
import de.htwberlin.lora_multihop_implementation.interfaces.ILoraCommands;
import de.htwberlin.lora_multihop_implementation.interfaces.MessageConstants;
import de.htwberlin.lora_multihop_visualisation.BluetoothService;
import de.htwberlin.lora_multihop_visualisation.SingletonDevice;

public class LoraHandler extends AppCompatActivity implements MessageConstants {

    private static final String TAG = "MessageHandler";

    private BluetoothService btService;
    private MessageParser parser;
    private ILoraCommands executor;
    private MessageProcessor processor;
    public static LoraHandler instance = null;

    public LoraHandler(BluetoothService btService) {
        this.btService = btService;
        this.executor = new LoraCommandsExecutor(this.btService);
        this.parser = new MessageParser(this.executor);
        this.processor = new MessageProcessor(this.executor);
    }

    public void processLoraResponse(String responseMsg) {
        if (parseMessage(responseMsg)) processMessage();
    }

    public BluetoothService getBtService() {
        return btService;
    }

    public MessageParser getParser() {
        return parser;
    }

    public ILoraCommands getExecutor() {
        return executor;
    }

    public MessageProcessor getProcessor() {
        return processor;
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

    public static LoraHandler getInstance(BluetoothService btService) {
        if (instance == null) instance = new LoraHandler(btService);
        return instance;
    }

    public static LoraHandler getInstance() {
        return instance;
    }

    private void initLocalHop() {
        LocalHop localHop = LocalHop.getInstance();
        String deviceAddress = SingletonDevice.getBluetoothDevice()
                .getAddress()
                .replace(":", "")
                .substring(12 - 4);
        localHop.setAddress(deviceAddress);
        localHop.setLatitude(50.123);
        localHop.setLongitude(40.123);
    }
}
