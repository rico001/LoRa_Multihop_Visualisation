package de.htwberlin.lora_multihop_visualisation.ParserTests;

import org.junit.Before;
import org.junit.Test;

import de.htwberlin.lora_multihop_logic.components.messages.AckMessage;
import de.htwberlin.lora_multihop_logic.components.messages.Message;
import de.htwberlin.lora_multihop_logic.components.parser.MessageParser;
import de.htwberlin.lora_multihop_logic.components.parser.ParserException;

import static junit.framework.TestCase.assertNull;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests ACK messages Parser
 * <p>
 * Test Cases:
 * <p>
 * - "ACK"
 * - if source Address is valid
 */
public class ParserAckMessagesTest {
    private MessageParser parser;

    @Before
    public void init() {
        parser = new MessageParser();
    }

    @Test
    public void givenValidAckMessageShouldReturnInstanceOfAckMessage() throws Exception {
        String inputMessage = "ACK;1234";
        Message ackMessage = parser.parseInput(inputMessage);
        assertThat(ackMessage, instanceOf(AckMessage.class));
    }

    @Test
    public void givenValidAckMessageShouldReturnInstanceOfAckMessage2() throws Exception {
        String inputMessage = "ACK;12DE";
        Message ackMessage = parser.parseInput(inputMessage);
        assertThat(ackMessage, instanceOf(AckMessage.class));
    }

    @Test
    public void givenValidAckMessageShouldReturnInstanceOfAckMessage3() throws Exception {
        String inputMessage = "ACK;1ED2";
        Message ackMessage = parser.parseInput(inputMessage);
        assertThat(ackMessage, instanceOf(AckMessage.class));
    }

    @Test
    public void givenInvalidAckMessageShouldNotBeAddedToQueue() throws Exception {
        String inputMessage = "AC;1233";
        Message ackMessage = parser.parseInput(inputMessage);
        assertNull(ackMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidAckMessageShouldThrowParserException() throws Exception {
        String inputMessage = "ACK;1234;0";
        parser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidAckMessageShouldThrowParserException2() throws Exception {
        String inputMessage = "ACK;124;43.453";
        parser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidAckMessageShouldThrowParserException3() throws Exception {
        String inputMessage = "ACK;123y4;43.453;54.232";
        parser.parseInput(inputMessage);
    }
}