package de.htwberlin.lora_multihop_implementation.components.lora;

import de.htwberlin.lora_multihop_implementation.interfaces.ILoraCommands;
import de.htwberlin.lora_multihop_implementation.interfaces.LoraCommandsConstants;
import de.htwberlin.lora_multihop_visualisation.BluetoothService;

/**
 * Implementiert das ILoraCommands Interface
 * 	*	BluetoothService als Abhängigkeit: Senden der Commands zum LoRa-Modul via Bluetooth-Modul (verbunden auf Breadboard)
 */
public class LoraCommandsExecutor implements ILoraCommands {

	private BluetoothService btService;

	public LoraCommandsExecutor(BluetoothService btService)	{
		this.btService = btService;
	}

	@Override
	public void test() {
		if(btService.isConnected()) {
			btService.write(LoraCommandsConstants.TEST_CMD_MSG.getBytes());
		}
	}

	@Override
	public void reset() {
		if(btService.isConnected())	{
			btService.write(LoraCommandsConstants.RESET_CMD_MSG.getBytes());
		}
	}

	@Override
	public void getVersion() {
		if(btService.isConnected())	{
			btService.write(LoraCommandsConstants.GET_VERSION_CMD_MSG.getBytes());
		}
	}

	@Override
	public void enterIdleMode() {
		if(btService.isConnected())	{
			btService.write(LoraCommandsConstants.ENTER_IDLE_MODE_CMD_MSG.getBytes());
		}
	}

	@Override
	public void enterSleepMode() {
		if(btService.isConnected())	{
			btService.write(LoraCommandsConstants.ENTER_SLEEP_MODE_CMD_MSG.getBytes());
		}
	}

	@Override
	public void exitSleepMode() {
		if(btService.isConnected())	{
			// TODO
		}
	}

	@Override
	public void enterReceiveMode() {
		if(btService.isConnected())	{
			btService.write(LoraCommandsConstants.ENTER_RECEIVE_MODE_CMD_MSG.getBytes());
		}
	}

	@Override
	public void receiveData() {
		if(btService.isConnected())	{
			// TODO
		}
	}

	@Override
	public void receiveDataTimeout() {
		if(btService.isConnected())	{
			// TODO
		}
	}

	@Override
	public void getRssiValue() {
		if(btService.isConnected())	{
			btService.write(LoraCommandsConstants.GET_RSSI_VALUE_CMD_MSG.getBytes());
		}
	}

	@Override
	public void setAddress(String addr) {
		String completeCommand = LoraCommandsConstants.SET_ADDRESS_CMD_MSG.replace("XXXX", addr);

		if(btService.isConnected())	{
			btService.write(completeCommand.getBytes());
		}
	}

	@Override
	public void getAddress() {
		if(btService.isConnected())	{
			btService.write(LoraCommandsConstants.GET_ADDRESS_CMD_MSG.getBytes());
		}
	}

	@Override
	public void setTargetAddress(String addr) {
		String completeCommand = LoraCommandsConstants.SET_TARGET_ADDRESS_CMD_MSG.replace("XXXX", addr);

		if(btService.isConnected())	{
			btService.write(completeCommand.getBytes());
		}
	}

	@Override
	public void getTargetAddress() {
		if(btService.isConnected())	{
			btService.write(LoraCommandsConstants.GET_TARGET_ADDRESS_CMD_MSG.getBytes());
		}
	}

	@Override
	public void setAddressFilter(int enable) {
		String completeCommand = LoraCommandsConstants.SET_ADDRESS_FILTER_CMD_MSG.replace("X", Integer.toString(enable));

		if(btService.isConnected())	{
			btService.write(completeCommand.getBytes());
		}
	}

	@Override
	public void getAddressFilter() {
		if(btService.isConnected())	{
			btService.write(LoraCommandsConstants.GET_ADDRESS_FILTER_CMD_MSG.getBytes());
		}
	}

	@Override
	public void configure(int frequency, int power, int signalBw, int spreadingFactor, int errorCoding, int crc, int implicitHeaderOn, int rxSingleOn, int frequencyHopOn, int hopPeriod, int rxPacketTimeout, int payloadLength, int preambleLength) {
		String completeCommand = LoraCommandsConstants.CONFIGURE_CMD_MSG;
		completeCommand.replace("frequency", Integer.toString(frequency));
		completeCommand.replace("power", Integer.toString(power));
		completeCommand.replace("signalBw", Integer.toString(signalBw));
		completeCommand.replace("spreadingFactor", Integer.toString(spreadingFactor));
		completeCommand.replace("errorCoding", Integer.toString(errorCoding));
		completeCommand.replace("crc", Integer.toString(crc));
		completeCommand.replace("implicitHeaderOn", Integer.toString(implicitHeaderOn));
		completeCommand.replace("rxSingleOn", Integer.toString(rxSingleOn));
		completeCommand.replace("frequencyHopOn", Integer.toString(frequencyHopOn));
		completeCommand.replace("hopPeriod", Integer.toString(hopPeriod));
		completeCommand.replace("rxPacketTimeout", Integer.toString(rxPacketTimeout));
		completeCommand.replace("payloadLength", Integer.toString(payloadLength));
		completeCommand.replace("preambleLength", Integer.toString(preambleLength));

		if(btService.isConnected())	{
			btService.write(completeCommand.getBytes());
		}
	}

	@Override
	public void save() {
		if(btService.isConnected())	{
			btService.write(LoraCommandsConstants.SAVE_CMD_MSG.getBytes());
		}
	}

	@Override
	public void send() {
		if(btService.isConnected())	{
			btService.write(LoraCommandsConstants.SEND_CMD_MSG.getBytes());
		}
	}

	@Override
	public void send(String data) {
		if(btService.isConnected())	{
			btService.write(data.getBytes());
		}
	}

	@Override
	public void enterDirectTransmission(String data) {
		if(btService.isConnected())	{
			btService.write(LoraCommandsConstants.ENTER_DIRECT_TRANSMISSION_CMD_MSG.getBytes());
		}
	}

	@Override
	public void exitDirectTransmission(String data) {
		if(btService.isConnected())	{
			btService.write(LoraCommandsConstants.EXIT_DIRECT_TRANSMISSION_CMD_MSG.getBytes());
		}
	}
}
