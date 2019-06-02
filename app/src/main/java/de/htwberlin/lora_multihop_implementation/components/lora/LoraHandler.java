package de.htwberlin.lora_multihop_implementation.components.lora;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import de.htwberlin.lora_multihop_implementation.components.messages.JoinReplyMessage;
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

    public LoraHandler(BluetoothService btService) {
        this.btService = btService;
        this.executor = new LoraCommandsExecutor(this.btService);
        this.parser = new MessageParser(this.executor);
        this.processor = new MessageProcessor(this.executor);
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
        } catch (ParserException e) {
            Log.e(TAG, e.getLocalizedMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, e.getLocalizedMessage());
        } catch (NullPointerException e) {
            Log.e(TAG, e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        Log.i(TAG, "unable message");
        return Boolean.FALSE;
    }

    private void processMessage() {
        processor.processMessage();
	}
}
