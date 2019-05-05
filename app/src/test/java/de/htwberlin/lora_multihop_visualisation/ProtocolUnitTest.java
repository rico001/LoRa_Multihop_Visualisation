package de.htwberlin.lora_multihop_visualisation;

import org.junit.Test;

/**
 * Tests for the protocol.
 * todo: what do we want to test?
 * todo: probably need to implement a test for each method implementing a corresponding AT command
 */
public class ProtocolUnitTest {
    @Test
    public void sendCommand() {
    }

    @Test
    public void configurationStringGeneration() {

    }

    @Test
    public void resetCommand() {
        // todo: check the reset command sent and correct
        // expect "AT+RST\r\n"
    }

    @Test
    public void commandTimeout() {
        // todo: check what happens if a command timed-out
    }

    @Test
    public void commandsOrder() {
        // todo: somehow check that the commands are send in the correct order
        // mock the Sender component or stub the Protocol.send method (depends on what architecture we want), record the order, compare with expected
    }

    @Test
    public void receiveData() {
        // todo: basically it should be an asynchronous listener (a thread?) that will invoke a handler on receiving
        // we need to simulate a sending message and check that the async handler fires and gets the same message we send
        // expect "LR, XXXX, XX, message", XXXX - source address (validate), XX - hex data length (range 0x01~0xFB), message is data with length of XX bytes
    }

    @Test
    public void receiveHandler() {
        // todo: test handler, what do we do on receiving a message?
        // we need to pass a message to the receive handler and check:
        // - that it doesn't throw any errors for a valid message
        // - throws an error for a message with invalid address
        // - throws an error for a message with invalid length or overall malformed message
    }
}