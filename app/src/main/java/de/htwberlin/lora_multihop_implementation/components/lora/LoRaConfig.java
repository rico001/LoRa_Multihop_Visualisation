package de.htwberlin.lora_multihop_implementation.components.lora;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This configurator represents a LoRa module configuration and allows to specify different options
 * before generating the actual configuration string that would be sent to the module.
 *
 * todo: validation
 */
public class LoRaConfig {
    public static final Map<Integer, String> bandwidthMap = ImmutableMap.<Integer, String>builder()
            .put(0, "7.8KHz")
            .put(1, "10.4KHz")
            .put(2, "15.6KHz")
            .put(3, "20.8KHz")
            .put(4, "31.2KHz")
            .put(5, "41.6KHz")
            .put(6, "62.5KHz")
            .put(7, "125KHz")
            .put(8, "250KHz")
            .put(9, "500KHz")
            .build();
    public static final Map<Integer, String> spreadingMap = ImmutableMap.<Integer, String>builder()
            .put(6, "64")
            .put(7, "128")
            .put(8, "256")
            .put(9, "512")
            .put(10, "1024")
            .put(11, "2048")
            .put(12, "4096")
            .build();
    public static final Map<Integer, String> errorCodingMap = ImmutableMap.<Integer, String>builder()
            .put(1, "4/5")
            .put(2, "4/6")
            .put(3, "4/7")
            .put(4, "4/8")
            .build();


    /**
     * In MHz, must be converted to HZ before sending to the module: 433000000.
     * Range: 410MHz - 470MHz
     */
    private int frequency = 410;
    /**
     * Range: 5dBm - 20dBm
     */
    private int power = 20;
    /**
     * Encoded bandwidth.
     * [0: 7.8KHz], [1: 10.4KHz], [2: 15.6KHz], [3: 20.8KHz], [4: 31.2KHz]
     * [5: 41.6KHz], [6: 62.5KHz], [7: 125KHz], [8: 250KHz], [9: 500KHz]
     */
    private int signalBw = 6;
    /**
     * Encoded spreading factor.
     * [6: 64], [7: 128], [8: 256], [9: 512]
     * [10: 1024], [11: 2048], [12: 4096]
     */
    private int spreadingFactor = 10;
    /**
     * Encoded error coding.
     * [1: 4/5], [2: 4/6], [3: 4/7], [4: 4/8]
     */
    private int errorCoding = 1;
    /**
     * Payload data CRC check.
     * [0: close], [1: open]
     */
    private int crc = 1;
    /**
     * [0: explicit], [1: implicit]
     */
    private int implicitHeaderOn = 0;
    /**
     * [0: continue], [1: single]
     */
    private int rxSingleOn = 0;
    /**
     * [0: not support], [1: support]
     */
    private int frequencyHopOn = 0;
    /**
     * [0: reverse]
     */
    private int hopPeriod = 0;
    /**
     * Milliseconds.
     * [1-65535]
     */
    private int rxPacketTimeout = 3000;
    /**
     * In "implicit" mode specifies the length of sent/received data:
     * length = user data length + 4
     *
     * Invalid under "explicit" mode.
     * [5-255]
     */
    private int payloadLength = 8;
    /**
     * [4-65535]
     */
    private int preambleLength = 4;

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSignalBw() {
        return signalBw;
    }

    public void setSignalBw(int signalBw) {
        this.signalBw = signalBw;
    }

    public int getSpreadingFactor() {
        return spreadingFactor;
    }

    public void setSpreadingFactor(int spreadingFactor) {
        this.spreadingFactor = spreadingFactor;
    }

    public int getErrorCoding() {
        return errorCoding;
    }

    public void setErrorCoding(int errorCoding) {
        this.errorCoding = errorCoding;
    }

    public int getCrc() {
        return crc;
    }

    public void setCrc(int crc) {
        this.crc = crc;
    }

    public int getImplicitHeaderOn() {
        return implicitHeaderOn;
    }

    public void setImplicitHeaderOn(int implicitHeaderOn) {
        this.implicitHeaderOn = implicitHeaderOn;
    }

    public int getRxSingleOn() {
        return rxSingleOn;
    }

    public void setRxSingleOn(int rxSingleOn) {
        this.rxSingleOn = rxSingleOn;
    }

    public int getFrequencyHopOn() {
        return frequencyHopOn;
    }

    public void setFrequencyHopOn(int frequencyHopOn) {
        this.frequencyHopOn = frequencyHopOn;
    }

    public int getHopPeriod() {
        return hopPeriod;
    }

    public void setHopPeriod(int hopPeriod) {
        this.hopPeriod = hopPeriod;
    }

    public int getRxPacketTimeout() {
        return rxPacketTimeout;
    }

    public void setRxPacketTimeout(int rxPacketTimeout) {
        this.rxPacketTimeout = rxPacketTimeout;
    }

    public int getPayloadLength() {
        return payloadLength;
    }

    public void setPayloadLength(int payloadLength) {
        this.payloadLength = payloadLength;
    }

    public int getPreambleLength() {
        return preambleLength;
    }

    public void setPreambleLength(int preambleLength) {
        this.preambleLength = preambleLength;
    }

    /**
     * Configuration format for the AT command:
     * AT+CFG=frequency,power,signalBw,spreadingFactor,errorCoding,crc,
     * implicitHeaderOn,rxSingleOn,frequencyHopOn,hopPeriod,rxPacketTimeout,
     * payloadLength,preambleLength\r\n
     */
    @Override
    public String toString() {
        List<Integer> pieces = Arrays.asList(
                frequency * 1000000,
                power,
                signalBw,
                spreadingFactor,
                errorCoding,
                crc,
                implicitHeaderOn,
                rxSingleOn,
                frequencyHopOn,
                hopPeriod,
                rxPacketTimeout,
                payloadLength,
                preambleLength
        );

        return Joiner.on(",").join(pieces);
    }
}
