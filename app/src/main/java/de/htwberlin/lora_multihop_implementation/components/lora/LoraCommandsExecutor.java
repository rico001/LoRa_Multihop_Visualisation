package de.htwberlin.lora_multihop_implementation.components.lora;

import de.htwberlin.lora_multihop_implementation.interfaces.ILoraCommands;
import de.htwberlin.lora_multihop_implementation.interfaces.LoraCommandsConstants;
import de.htwberlin.lora_multihop_visualisation.BluetoothService;

/**
 * Implementiert das ILoraCommands Interface
 * 	*	BluetoothService als Abhängigkeit: Senden der Commands zum LoRa-Modul via Bluetooth-Modul (verbunden auf Breadboard)
 */
public class LoraCommandsExecutor implements ILoraCommands, LoraCommandsConstants {

	private final static String AT_POSTFIX = "\r\n";
	private BluetoothService btService;

	public LoraCommandsExecutor(BluetoothService btService)	{
		this.btService = btService;
	}

	@Override
	public void test() {
		if(btService.isConnected()) {
			btService.write((TEST_CMD_MSG + AT_POSTFIX).getBytes());
		}
	}

	@Override
	public void reset() {
		if(btService.isConnected())	{
			btService.write((RESET_CMD_MSG + AT_POSTFIX).getBytes());
		}
	}

	@Override
	public void getVersion() {
		if(btService.isConnected())	{
			btService.write((GET_VERSION_CMD_MSG + AT_POSTFIX).getBytes());
		}
	}

	@Override
	public void enterIdleMode() {
		if(btService.isConnected())	{
			btService.write((ENTER_IDLE_MODE_CMD_MSG + AT_POSTFIX).getBytes());
		}
	}

	@Override
	public void enterSleepMode() {
		if(btService.isConnected())	{
			btService.write((ENTER_SLEEP_MODE_CMD_MSG + AT_POSTFIX).getBytes());
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
			btService.write((ENTER_RECEIVE_MODE_CMD_MSG + AT_POSTFIX).getBytes());
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
			btService.write((GET_RSSI_VALUE_CMD_MSG + AT_POSTFIX).getBytes());
		}
	}

	@Override
	public void setAddress(String addr) {
		String completeCommand = SET_ADDRESS_CMD_MSG.replace("XXXX", addr);

		if(btService.isConnected())	{
			btService.write((completeCommand + AT_POSTFIX).getBytes());
		}
	}

	@Override
	public void getAddress() {
		if(btService.isConnected())	{
			btService.write((GET_ADDRESS_CMD_MSG + AT_POSTFIX).getBytes());
		}
	}

	@Override
	public void setTargetAddress(String addr) {
		String completeCommand = SET_TARGET_ADDRESS_CMD_MSG.replace("XXXX", addr);

		if(btService.isConnected())	{
			btService.write((completeCommand + AT_POSTFIX).getBytes());
		}
	}

	@Override
	public void getTargetAddress() {
		if(btService.isConnected())	{
			btService.write((GET_TARGET_ADDRESS_CMD_MSG + AT_POSTFIX).getBytes());
		}
	}

	@Override
	public void setAddressFilter(int enable) {
		String completeCommand = SET_ADDRESS_FILTER_CMD_MSG.replace("X", Integer.toString(enable));

		if(btService.isConnected())	{
			btService.write((completeCommand + AT_POSTFIX).getBytes());
		}
	}

	@Override
	public void getAddressFilter() {
		if(btService.isConnected())	{
			btService.write((GET_ADDRESS_FILTER_CMD_MSG + AT_POSTFIX).getBytes());
		}
	}

	@Override
	public void configure(LoRaConfig config){
		String completeCommand = CONFIGURE_CMD_MSG.replace("XX", config.toString());

		if(btService.isConnected())	{
			btService.write((completeCommand + AT_POSTFIX).getBytes());
		}
	}

	@Override
	public void save() {
		if(btService.isConnected())	{
			btService.write((SAVE_CMD_MSG + AT_POSTFIX).getBytes());
		}
	}

	@Override
    public void send(int bytes) {
        String completeCommand = SEND_CMD_MSG.replace("XX", Integer.toString(bytes));
		if(btService.isConnected())	{
            btService.write((completeCommand + AT_POSTFIX).getBytes());
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
			btService.write((ENTER_DIRECT_TRANSMISSION_CMD_MSG + AT_POSTFIX).getBytes());
		}
	}

	@Override
	public void exitDirectTransmission(String data) {
		if(btService.isConnected())	{
			btService.write((EXIT_DIRECT_TRANSMISSION_CMD_MSG + AT_POSTFIX).getBytes());
		}
	}
}
