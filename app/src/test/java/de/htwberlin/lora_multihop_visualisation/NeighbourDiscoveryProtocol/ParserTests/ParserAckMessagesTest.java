package de.htwberlin.lora_multihop_visualisation.NeighbourDiscoveryProtocol.ParserTests;

import org.junit.Before;
import org.junit.Test;

import de.htwberlin.lora_multihop_implementation.components.messages.AckMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.Message;
import de.htwberlin.lora_multihop_implementation.components.parser.MessageParser;
import de.htwberlin.lora_multihop_implementation.components.parser.ParserException;
import de.htwberlin.lora_multihop_implementation.components.queue.NeighbourDiscoveryProtocolQueue;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * Tests ACK messages Parser
 *
 * Test Cases:
 *
 *  - "ACK"
 *  - if source Address is valid
 *
 */
public class ParserAckMessagesTest {

    private NeighbourDiscoveryProtocolQueue queue;

    @Before
    public void init() {
        queue = NeighbourDiscoveryProtocolQueue.getInstance();
    }

    @Test
    public void givenValidAckMessageShouldReturnInstanceOfAckMessage() throws Exception {
        String inputMessage = "ACK;1234";
        MessageParser.parseInput(inputMessage);
        Message ackMessage = (Message) queue.poll();
        assertThat(ackMessage, instanceOf(AckMessage.class));
    }

    @Test
    public void givenValidAckMessageShouldReturnInstanceOfAckMessage2() throws Exception {
        String inputMessage = "ACK;12DE";
        MessageParser.parseInput(inputMessage);
        Message ackMessage = (Message) queue.poll();
        assertThat(ackMessage, instanceOf(AckMessage.class));
    }

    @Test
    public void givenValidAckMessageShouldReturnInstanceOfAckMessage3() throws Exception {
        String inputMessage = "ACK;1ED2";
        MessageParser.parseInput(inputMessage);
        Message ackMessage = (Message) queue.poll();
        assertThat(ackMessage, instanceOf(AckMessage.class));
    }

    @Test
    public void givenInvalidAckMessageShouldNotBeAddedToQueue() throws Exception {
        int queueSizeBefore = queue.size();
        String inputMessage = "AC;1233";
        MessageParser.parseInput(inputMessage);
        assertEquals(queueSizeBefore, 0);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidAckMessageShouldThrowParserException() throws Exception {
        String inputMessage = "ACK;1234;0";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidAckMessageShouldThrowParserException2() throws Exception {
        String inputMessage = "ACK;124;43.453";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidAckMessageShouldThrowParserException3() throws Exception {
        String inputMessage = "ACK;123y4;43.453;54.232";
        MessageParser.parseInput(inputMessage);
    }
}