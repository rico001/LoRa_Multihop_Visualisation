package de.htwberlin.lora_multihop_visualisation.ParserTests;

import org.junit.Before;
import org.junit.Test;

import de.htwberlin.lora_multihop_logic.components.messages.Message;
import de.htwberlin.lora_multihop_logic.components.parser.MessageParser;
import de.htwberlin.lora_multihop_logic.components.parser.ParserException;

import static junit.framework.TestCase.assertNull;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests LEAVE messages Parser
 * <p>
 * Test Cases:
 * <p>
 * - "LEAVE"
 * - if source Address is valid
 */
public class ParserLeaveMessagesTest {
    private MessageParser parser;

    @Before
    public void init() {
        parser = new MessageParser();
    }

    @Test
    public void givenValidLeaveMessageShouldReturnInstanceOfLeaveMessage() throws Exception {
        String inputMessage = "LEAVE;1234";
        Message leaveMessage = parser.parseInput(inputMessage);
        assertThat(leaveMessage, instanceOf(LeaveMessage.class));
    }

    @Test
    public void givenValidLeaveMessageShouldReturnInstanceOfLeaveMessage2() throws Exception {
        String inputMessage = "LEAVE;12DE";
        Message leaveMessage = parser.parseInput(inputMessage);
        assertThat(leaveMessage, instanceOf(LeaveMessage.class));
    }

    @Test
    public void givenValidLeaveMessageShouldReturnInstanceOfLeaveMessage3() throws Exception {
        String inputMessage = "LEAVE;1ED2";
        Message leaveMessage = parser.parseInput(inputMessage);
        assertThat(leaveMessage, instanceOf(LeaveMessage.class));
    }

    @Test
    public void givenInvalidLeaveMessageShouldNotBeAddedToQueue() throws Exception {
        String inputMessage = "LEA;1233";
        Message leaveMessage = parser.parseInput(inputMessage);
        assertNull(leaveMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidLeaveMessageShouldThrowParserException() throws Exception {
        String inputMessage = "LEAVE;1234;0";
        parser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidLeaveMessageShouldThrowParserException2() throws Exception {
        String inputMessage = "LEAVE;124;43.453";
        parser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidLeaveMessageShouldThrowParserException3() throws Exception {
        String inputMessage = "LEAVE;123y4;43.453;54.232";
        parser.parseInput(inputMessage);
    }
}

