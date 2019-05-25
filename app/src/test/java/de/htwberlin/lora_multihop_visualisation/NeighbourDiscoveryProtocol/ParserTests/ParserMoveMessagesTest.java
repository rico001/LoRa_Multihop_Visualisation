package de.htwberlin.lora_multihop_visualisation.NeighbourDiscoveryProtocol.ParserTests;

import org.junit.Before;
import org.junit.Test;

import de.htwberlin.lora_multihop_implementation.components.messages.JoinMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.Message;
import de.htwberlin.lora_multihop_implementation.components.messages.MoveMessage;
import de.htwberlin.lora_multihop_implementation.components.parser.MessageParser;
import de.htwberlin.lora_multihop_implementation.components.parser.ParserException;
import de.htwberlin.lora_multihop_implementation.components.queue.NeighbourDiscoveryProtocolQueue;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * Tests MOVE messages Parser
 *
 * Test Cases:
 *
 *  - "MOVE"
 *  - if source Address is valid
 *  - if latitude/longitude can be parsed to double
 *  - missing / to much parts in message
 *
 */

public class ParserMoveMessagesTest {

    private NeighbourDiscoveryProtocolQueue queue;

    @Before
    public void init(){
        queue = NeighbourDiscoveryProtocolQueue.getInstance();
    }

    @Test
    public void givenValidMoveMessageShouldReturnInstanceOfMoveMessage() throws Exception {
        String inputMessage = "MOVE;1234;43.453;23.2232";
        MessageParser.parseInput(inputMessage);
        Message moveMessage = (Message) queue.poll();
        assertThat(moveMessage, instanceOf(MoveMessage.class));
    }

    @Test
    public void givenValidMoveMessageShouldReturnInstanceOfMoveMessage2() throws Exception {
        String inputMessage = "MOVE;12DE;21.453;45.2232";
        MessageParser.parseInput(inputMessage);
        Message moveMessage = (Message) queue.poll();
        assertThat(moveMessage, instanceOf(MoveMessage.class));
    }

    @Test
    public void givenValidMoveMessageShouldReturnInstanceOfMoveMessage3() throws Exception {
        String inputMessage = "MOVE;FFED;43.453;23.2232";
        MessageParser.parseInput(inputMessage);
        Message moveMessage = (Message) queue.poll();
        assertThat(moveMessage, instanceOf(MoveMessage.class));
    }

    @Test
    public void givenInvalidMoveMessageShouldNotBeAddedToQueue() throws Exception {
        int queueSizeBefore = queue.size();
        String inputMessage = "MVE;1234;43.453;23.2232";
        MessageParser.parseInput(inputMessage);
        assertEquals(queueSizeBefore,0);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidMoveMessageShouldThrowParserException() throws Exception {
        String inputMessage = "MOVE;1234;43.453;23.2232;0";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidMoveMessageShouldThrowParserException2() throws Exception {
        String inputMessage = "MOVE;1234;43.453";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidMoveMessageShouldThrowParserException3() throws Exception {
        String inputMessage = "MOVE;123y4;43.453;54.232";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidMoveMessageShouldThrowNumberformatException() throws Exception {
        String inputMessage = "MOVE;1234;43,453;54.232";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidMoveMessageShouldThrowNumberformatException2() throws Exception {
        String inputMessage = "MOVE;1234;43s453;54.232";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidMoveMessageShouldThrowNumberformatException3() throws Exception {
        String inputMessage = "MOVE;1234;43.453;54.232l";
        MessageParser.parseInput(inputMessage);
    }
}
