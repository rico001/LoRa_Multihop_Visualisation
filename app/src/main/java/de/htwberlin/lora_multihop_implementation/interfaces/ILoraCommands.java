package de.htwberlin.lora_multihop_implementation.interfaces;

import de.htwberlin.lora_multihop_implementation.Configurator;

/**
 * ILoraCommands.java
 * implements the basic functionality / possible commands
 * for the HIMO-01(M) LoRa module
 */
public interface ILoraCommands {

    /**
     * TEST CMD
     *
     * cmd format				AT\r\n
     * reply data format		AT,OK\r\n
     */
    void test();

    /**
     * RESET MODULE
     *
     * cmd format				AT+RST\r\n
     * reply data format		AT,OK\r\n
     */
    void reset();

    /**
     * READ VERSION
     *
     * cmd format				AT+VER\r\n
     * reply data format		AT,V0.3,OK\r\n
     *
     * @return version
     */
    String getVersion();

    /**
     * ENTER IDLE MODE
     *
     * cmd format				AT+IDLE\r\n
     * reply data format		AT,OK\r\n
     */
    void enterIdleMode();

    /**
     * ENTER SLEEP MODE
     *
     * cmd format				AT+SLEEP=1\r\n
     * reply data format		AT,OK\r\n
     */
    void enterSleepMode();

    /**
     * EXIT SLEEP MODE
     *
     * cmd format
     * reply data format		AT,WakeUp\r\n
     */
    void exitSleepMode();

    /**
     * ENTER RECEIVE MODE
     *
     * cmd format				AT+RX\r\n
     * reply data format		AT,OK\r\n
     *
     * single					after receive auto enter idle mode
     * OR
     * continuous				always in receiving state
     */
    void enterReceiveMode();

    /**
     * RECEIVE DATA (async)
     *
     * cmd format
     * reply data format		LR,XXXX,XX,ASFASDFASFD
     *
     * XXXX					HEX representation of source address (0000 - FFFF)
     * FFFF : broadcast (value : 0xffff)
     * XX						HEX byte data length (01 - FB = 1 - 250)
     * ASFASDFASFD				data
     */
    void receiveData();

    /**
     * RECEIVE DATA TIMEOUT (async)
     *
     * cmd format
     * reply data format		AT,TimeOut\r\n
     */
    void receiveDataTimeout();

    /**
     * QUERY RSSI VALUE
     *
     * cmd format				AT+RSSI?\r\n
     * reply data format		AT,-XXX,OK\r\n
     *
     * -XXX					HEX representation
     * -63dB -> response "AT,-063,OK\r\n"
     *
     * @return RSSI value
     */
    String getRssiValue();

    /**
     * SET MODULE ADDRESS
     *
     * cmd format				AT+ADDR=XXXX\r
     * reply data format		AT,OK\r\n
     *
     * XXXX					HEX representation of address (0000 - FFFF)
     * FFFF : broadcast (value : 0xffff)
     */
    void setAddress();

    /**
     * GET MODULE ADDRESS
     *
     * cmd format				AT+ADDR?\r\n
     * reply data format		AT,XXXX,OK\r\n
     *
     * XXXX					HEX representation of address (0000 - FFFF)
     * FFFF : broadcast (value : 0xffff)
     *
     * @return module address
     */
    String getAddress();

    /**
     * SET TARGET ADDRESS
     *
     * cmd format				AT+DEST=XXXX\r\
     * reply data format		AT,OK\r\n
     *
     * XXXX					HEX representation of address (0000 - FFFF)
     * FFFF : broadcast (value : 0xffff)
     */
    void setTargetAddress();

    /**
     * GET TARGET ADDRESS
     *
     * cmd format				AT+DEST?\r\n
     * reply data format		AT,XXXX,OK\r\n
     *
     * XXXX					HEX representation of address (0000 - FFFF)
     * FFFF : broadcast (value : 0xffff)
     *
     * @return target adress
     */
    String getTargetAddress();

    /**
     * SET ADDRESS FILTER
     *
     * cmd format				AT+ADDREN=X\r\n
     * reply data format		AT,OK\r\n
     *
     * X						int value
     * enable: 1
     * disable: 0 (default)
     *
     * @param enable 1 -> enable || 0 -> disable
     */
    void setAddressFilter(int enable);

    /**
     * GET ADDRESS FILTER
     *
     * cmd format				AT+ADDREN?\r\n
     * reply data format		AT,X,OK\r\n
     *
     * X						int value
     * enabled: 1
     * disabled: 0 (default)
     *
     * @return true -> active | false -> inactive
     */
    boolean getAddressFilter();

    /**
     * CONFIGURE MODULE
     *
     * AT+CFG=config string\r\n
     * reply data format		AT,OK\r\n
     */
    void configure(Configurator configurator);

    /**
     * SAVE CONFIG, OWN ADDRESS & TARGET ADDRESS
     *
     * cmd format				AT+SAVE\r\n
     * reply data format		AT,OK\r\n
     */
    void save();

    /**
     * SEND DATA
     *
     * cmd format				AT+SEND=XX\r\n
     * reply data format		AT,OK\r\n			->	ready for receiving data
     * AT,SENDING\r\n		->	module receives data from serial
     * AT,SENDED\r\n		->	send finished
     *
     * XX						HEX byte send-data length (01 - FB = 1 - 250)
     * more data than specified gets abandoned
     */
    void send(String data);

    /**
     * ENTER DIRECT TRANSMISSION
     *
     * cmd format				AT+TSP\r\n
     * reply data format		AT,OK\r\n
     */
    void enterDirectTransmission(String data);

    /**
     * EXIT DIRECT TRANSMISSION
     *
     * ensure					idle state is active
     * recommended				suitable delay before sending command
     *
     * cmd format				+++
     * reply data format		AT,OK\r\n			->	success
     * AT,busy...\r\n		->	fail
     * cause: still sending data
     */
    void exitDirectTransmission(String data);

    /**
     * @param data string to calc the length for
     * @return byte length of string
     */
    int calculateByteLength(String data);

    /**
     * @param data string to convert to hex representation
     * @return hex representation of string
     */
    String convertToHex(String data);
}
