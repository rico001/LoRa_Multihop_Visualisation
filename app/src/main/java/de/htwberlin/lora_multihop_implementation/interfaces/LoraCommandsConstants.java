package de.htwberlin.lora_multihop_implementation.interfaces;

public interface LoraCommandsConstants {
	
	/**
	 * LORA COMMANDS FOR EXECUTION AS CONSTANTS IN STRING FORMAT
	 */
	String TEST_CMD_MSG = "AT\\r\\n";

	String RESET_CMD_MSG = "AT+RST\\r\\n";

	String GET_VERSION_CMD_MSG = "AT+VER\\r\\n";

	String ENTER_IDLE_MODE_CMD_MSG = "AT+IDLE\\r\\n";
	String ENTER_SLEEP_MODE_CMD_MSG = "AT+SLEEP=1\\r\\n";
	String ENTER_RECEIVE_MODE_CMD_MSG = "AT+RX\\r\\n";

	String GET_RSSI_VALUE_CMD_MSG = "AT+RSSI?\\r\\n";

	String SET_ADDRESS_CMD_MSG = "AT+ADDR=XXXX\\r";
	String GET_ADDRESS_CMD_MSG = "AT+ADDR?\\r\\n";

	String SET_TARGET_ADDRESS_CMD_MSG = "AT+DEST=XXXX\\r\\n";
	String GET_TARGET_ADDRESS_CMD_MSG = "AT+DEST?\\r\\n";

	String SET_ADDRESS_FILTER_CMD_MSG = "AT+ADDREN=X\\r\\n";
	String GET_ADDRESS_FILTER_CMD_MSG = "AT+ADDREN?\\r\\n";

	String CONFIGURE_CMD_MSG = "AT+CFG=frequency,power,signalBw,spreadingFactor,errorCoding,crc,implicitHeaderOn,rxSingleOn,frequencyHopOn,hopPeriod,rxPacketTimeout,payloadLength,preambleLength\\r\\n";
	String SAVE_CMD_MSG = "AT+SAVE\\r\\n";

	String SEND_CMD_MSG = "AT+SEND=XX\\r\\n";

	String ENTER_DIRECT_TRANSMISSION_CMD_MSG = "AT+TSP\\r\\n";

	String EXIT_DIRECT_TRANSMISSION_CMD_MSG = "+++";

	/**
	 * LORA REPLIES WHEN RECEIVING AS CONSTANTS IN STRING FORMAT
	 */
	String OK_REPLY_MSG = "AT,OK\\r\\n";
	String VERSION_REPLY_MSG = "AT,VERSION,OK\\r\\n";
	String EXIT_SLEEP_MODE_REPLY_MSG = "AT,WakeUp\\r\\n";
	String RECEIVE_DATA_REPLY_MSG = "LR,XXXX,XX,ASFASDFASFD";
	String RECEIVE_DATA_TIMEOUT_REPLY_MSG = "AT,TimeOut\\r\\n";
	String GET_RSSI_VALUE_REPLY_MSG = "AT+RSSI?\\r\\n";

	String GET_ADDRESS_REPLY_MSG = "AT,XXXX,OK\\r\\n";
	String GET_TARGET_ADDRESS_REPLY_MSG = "AT,XXXX,OK\\r\\n";

	String GET_ADDRESS_FILTER_REPLY_MSG = "AT,X,OK\\r\\n";

	String SENDING_DATA_REPLY_MSG = "AT,SENDING\\r\\n";
	String SENDED_DATA_REPLY_MSG = "AT,SENDED\\r\\n";

	String EXIT_DIRECT_TRANSMISSION_ERROR_REPLY_MSG = "AT,busy...\\r\\n";
}
