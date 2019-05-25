package de.htwberlin.lora_multihop_visualisation.NeighbourDiscoveryProtocol.ParserTests;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.instanceOf;

import de.htwberlin.lora_multihop_implementation.components.messages.JoinMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.Message;
import de.htwberlin.lora_multihop_implementation.components.parser.MessageParser;
import de.htwberlin.lora_multihop_implementation.components.parser.ParserException;
import de.htwberlin.lora_multihop_implementation.components.queue.NeighbourDiscoveryProtocolQueue;

/**
 *
 * Tests JOIN messages Parser
 *
 * Test Cases:
 *
 *  - "JOIN"
 *  - if source Address is valid
 *  - if latitude/longitude can be parsed to double
 *  - missing / to much parts in message
 *
 */
public class ParseJoinMessagesTest {

    private NeighbourDiscoveryProtocolQueue queue;

    @Before
    public void init(){
         queue = NeighbourDiscoveryProtocolQueue.getInstance();
    }

    @Test
    public void givenValidJoinMessageShouldReturnInstanceOfJoinMessage() throws Exception {
        String inputMessage = "JOIN;1234;43.453;23.2232";
        MessageParser.parseInput(inputMessage);
        Message joinMessage = (Message) queue.poll();
        assertThat(joinMessage, instanceOf(JoinMessage.class));
    }

    @Test
    public void givenValidJoinMessageShouldReturnInstanceOfJoinMessage2() throws Exception {
        String inputMessage = "JOIN;12DE;21.453;45.2232";
        MessageParser.parseInput(inputMessage);
        Message joinMessage = (Message) queue.poll();
        assertThat(joinMessage, instanceOf(JoinMessage.class));
    }

    @Test
    public void givenValidJoinMessageShouldReturnInstanceOfJoinMessage3() throws Exception {
        String inputMessage = "JOIN;FFED;43.453;23.2232";
        MessageParser.parseInput(inputMessage);
        Message joinMessage = (Message) queue.poll();
        assertThat(joinMessage, instanceOf(JoinMessage.class));
    }

    @Test
    public void givenInvalidJoinMessageShouldNotBeAddedToQueue() throws Exception {
        int queueSizeBefore = queue.size();
        String inputMessage = "JIN;1234;43.453;23.2232";
        MessageParser.parseInput(inputMessage);
        assertEquals(queueSizeBefore,0);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidJoinMessageShouldThrowParserException() throws Exception {
        String inputMessage = "JOIN;1234;43.453;23.2232;0";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidJoinMessageShouldThrowParserException2() throws Exception {
        String inputMessage = "JOIN;1234;43.453";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidJoinMessageShouldThrowParserException3() throws Exception {
        String inputMessage = "JOIN;123y4;43.453;54.232";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidJoinMessageShouldThrowNumberformatException() throws Exception {
        String inputMessage = "JOIN;1234;43,453;54.232";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidJoinMessageShouldThrowNumberformatException2() throws Exception {
        String inputMessage = "JOIN;1234;43s453;54.232";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidJoinMessageShouldThrowNumberformatException3() throws Exception {
        String inputMessage = "JOIN;1234;43.453;54.232l";
        MessageParser.parseInput(inputMessage);
    }
}
