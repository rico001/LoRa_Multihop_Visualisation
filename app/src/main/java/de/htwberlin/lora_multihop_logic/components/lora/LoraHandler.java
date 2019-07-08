package de.htwberlin.lora_multihop_logic.components.lora;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import de.htwberlin.lora_multihop_logic.NeighbourSetDataHandler;
import de.htwberlin.lora_multihop_logic.components.model.LocalHop;
import de.htwberlin.lora_multihop_logic.components.model.NeighbourSet;
import de.htwberlin.lora_multihop_logic.components.parser.MessageParser;
import de.htwberlin.lora_multihop_logic.components.parser.ParserException;
import de.htwberlin.lora_multihop_logic.components.processor.MessageProcessor;
import de.htwberlin.lora_multihop_logic.enums.ELoraNodeState;
import de.htwberlin.lora_multihop_logic.interfaces.ILoraCommands;
import de.htwberlin.lora_multihop_logic.interfaces.MessageConstants;
import de.htwberlin.lora_multihop_visualisation.BluetoothService;
import de.htwberlin.lora_multihop_visualisation.SingletonDevice;
import de.htwberlin.lora_multihop_visualisation.fragments.MapFragment;

public class LoraHandler extends AppCompatActivity implements MessageConstants {

    private static final String TAG = "MessageHandler";

    private BluetoothService btService;

    private ILoraCommands executor;
    private MessageParser parser;
    private MessageProcessor processor;

    private NeighbourSetDataHandler neighbourSetDataHandler;

    public LoraHandler(BluetoothService btService, NeighbourSetDataHandler neighbourSetDataHandler) {
        this.btService = btService;
        this.neighbourSetDataHandler = neighbourSetDataHandler;

        this.executor = new LoraCommandsExecutor(this.btService);
        this.parser = new MessageParser(this.executor);
        this.processor = new MessageProcessor(this.executor, this.neighbourSetDataHandler);
    }

    public void processLoraResponse(String responseMsg) {
        if (parseMessage(responseMsg)) processMessage();
    }

    private boolean parseMessage(String message) {
        try {
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
