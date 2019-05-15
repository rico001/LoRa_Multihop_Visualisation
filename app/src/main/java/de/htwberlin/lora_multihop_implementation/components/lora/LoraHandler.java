package de.htwberlin.lora_multihop_implementation.components.lora;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import de.htwberlin.lora_multihop_implementation.interfaces.ILoraCommands;
import de.htwberlin.lora_multihop_implementation.interfaces.MessageConstants;
import de.htwberlin.lora_multihop_visualisation.BluetoothService;
import de.htwberlin.lora_multihop_visualisation.SingletonDevice;

public class LoraHandler extends AppCompatActivity implements MessageConstants {

	private BluetoothService btService;
	private ILoraCommands loraCommandsExecutor;
	private final Handler responseHandler = new Handler() {
		@Override
		public synchronized void handleMessage(Message msg) {
			switch (msg.what) {
				case STATE_CONNECTING:
					System.out.println("Verbindung mit " + SingletonDevice.getBluetoothDevice().getName() + " wird aufgebaut");
					break;
				case STATE_CONNECTED:
					System.out.println("Verbindung ist aufgebaut");
					break;
				case MESSAGE_READ:
					byte[] readBuf = (byte[]) msg.obj;
					// construct a string from the valid bytes in the buffer
					String readMessage = new String(readBuf);
					processLoraResponse(readMessage.trim());
					break;
				case MESSAGE_ERROR:
					System.out.println("MSG ERROR");
					break;
			}
		}
	};

	public LoraHandler()	{
		this.initBluetoothService();

		this.loraCommandsExecutor = new LoraCommandsExecutor(btService);
	}

	private void initBluetoothService()	{
		try {
			btService = new BluetoothService(this, responseHandler, SingletonDevice.getBluetoothDevice());
			btService.connectWithBluetoothDevice();
		} catch (NullPointerException e) {
			System.out.println("BT issue --- " + e.getStackTrace());
		}
	}

	private static void processLoraResponse(String responseMsg)	{
		System.out.println(">> " + responseMsg);
	}

	public BluetoothService getBtService() {
		return btService;
	}

	public ILoraCommands getLoraCommandsExecutor() {
		return loraCommandsExecutor;
	}

	public Handler getResponseHandler() {
		return responseHandler;
	}
}
