package de.htwberlin.lora_multihop_implementation.interfaces;

public interface LoraCommandsConstants {

	/**
	 * LORA COMMANDS FOR EXECUTION AS CONSTANTS IN STRING FORMAT
	 */
	String TEST_CMD_MSG = "AT";

	String RESET_CMD_MSG = "AT+RST";

	String GET_VERSION_CMD_MSG = "AT+VER";

	String ENTER_IDLE_MODE_CMD_MSG = "AT+IDLE";
	String ENTER_SLEEP_MODE_CMD_MSG = "AT+SLEEP=1";
	String ENTER_RECEIVE_MODE_CMD_MSG = "AT+RX";

	String GET_RSSI_VALUE_CMD_MSG = "AT+RSSI?";

	String SET_ADDRESS_CMD_MSG = "AT+ADDR=XXXX";
	String GET_ADDRESS_CMD_MSG = "AT+ADDR?";

	String SET_TARGET_ADDRESS_CMD_MSG = "AT+DEST=XXXX";
	String GET_TARGET_ADDRESS_CMD_MSG = "AT+DEST?";

	String SET_ADDRESS_FILTER_CMD_MSG = "AT+ADDREN=X";
	String GET_ADDRESS_FILTER_CMD_MSG = "AT+ADDREN?";

	String CONFIGURE_CMD_MSG = "AT+CFG=frequency,power,signalBw,spreadingFactor,errorCoding,crc,implicitHeaderOn,rxSingleOn,frequencyHopOn,hopPeriod,rxPacketTimeout,payloadLength,preambleLength";
	String SAVE_CMD_MSG = "AT+SAVE";

	String SEND_CMD_MSG = "AT+SEND=XX";

	String ENTER_DIRECT_TRANSMISSION_CMD_MSG = "AT+TSP";

	String EXIT_DIRECT_TRANSMISSION_CMD_MSG = "+++";

	/**
	 * LORA REPLIES WHEN RECEIVING AS CONSTANTS IN STRING FORMAT
	 */
	String OK_REPLY_MSG = "AT,OK";
	String VERSION_REPLY_MSG = "AT,VERSION,OK";
	String EXIT_SLEEP_MODE_REPLY_MSG = "AT,WakeUp";
	String RECEIVE_DATA_REPLY_MSG = "LR,XXXX,XX,ASFASDFASFD";
	String RECEIVE_DATA_TIMEOUT_REPLY_MSG = "AT,TimeOut";
	String GET_RSSI_VALUE_REPLY_MSG = "AT+RSSI?";

	String GET_ADDRESS_REPLY_MSG = "AT,XXXX,OK";
	String GET_TARGET_ADDRESS_REPLY_MSG = "AT,XXXX,OK";

	String GET_ADDRESS_FILTER_REPLY_MSG = "AT,X,OK";

	String SENDING_DATA_REPLY_MSG = "AT,SENDING";
	String SENDED_DATA_REPLY_MSG = "AT,SENDED";

	String EXIT_DIRECT_TRANSMISSION_ERROR_REPLY_MSG = "AT,busy...";
}