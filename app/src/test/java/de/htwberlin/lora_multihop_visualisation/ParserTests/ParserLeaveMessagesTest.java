package de.htwberlin.lora_multihop_visualisation.ParserTests;

import org.junit.Before;
import org.junit.Test;

import de.htwberlin.lora_multihop_implementation.components.messages.LeaveMessage;
import de.htwberlin.lora_multihop_implementation.components.messages.Message;
import de.htwberlin.lora_multihop_implementation.components.parser.MessageParser;
import de.htwberlin.lora_multihop_implementation.components.parser.ParserException;
import de.htwberlin.lora_multihop_implementation.components.queue.NeighbourDiscoveryProtocolQueue;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * Tests LEAVE messages Parser
 *
 * Test Cases:
 *
 *  - "LEAVE"
 *  - if source Address is valid
 *
 */
public class ParserLeaveMessagesTest {

    private NeighbourDiscoveryProtocolQueue queue;

    @Before
    public void init(){
        queue = NeighbourDiscoveryProtocolQueue.getInstance();
    }

    @Test
    public void givenValidLeaveMessageShouldReturnInstanceOfLeaveMessage() throws Exception {
        String inputMessage = "LEAVE;1234";
        MessageParser.parseInput(inputMessage);
        Message leaveMessage = (Message) queue.poll();
        assertThat(leaveMessage, instanceOf(LeaveMessage.class));
    }

    @Test
    public void givenValidLeaveMessageShouldReturnInstanceOfLeaveMessage2() throws Exception {
        String inputMessage = "LEAVE;12DE";
        MessageParser.parseInput(inputMessage);
        Message leaveMessage= (Message) queue.poll();
        assertThat(leaveMessage, instanceOf(LeaveMessage.class));
    }

    @Test
    public void givenValidLeaveMessageShouldReturnInstanceOfLeaveMessage3() throws Exception {
        String inputMessage = "LEAVE;1ED2";
        MessageParser.parseInput(inputMessage);
        Message leaveMessage = (Message) queue.poll();
        assertThat(leaveMessage, instanceOf(LeaveMessage.class));
    }

    @Test
    public void givenInvalidLeaveMessageShouldNotBeAddedToQueue() throws Exception {
        int queueSizeBefore = queue.size();
        String inputMessage = "LEA;1233";
        MessageParser.parseInput(inputMessage);
        assertEquals(queueSizeBefore,0);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidLeaveMessageShouldThrowParserException() throws Exception {
        String inputMessage = "LEAVE;1234;0";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidLeaveMessageShouldThrowParserException2() throws Exception {
        String inputMessage = "LEAVE;124;43.453";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidLeaveMessageShouldThrowParserException3() throws Exception {
        String inputMessage = "LEAVE;123y4;43.453;54.232";
        MessageParser.parseInput(inputMessage);
    }
}

