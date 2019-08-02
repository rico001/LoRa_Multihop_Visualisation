package de.htwberlin.lora_multihop_visualisation.ParserTests;

import org.junit.Before;
import org.junit.Test;

import de.htwberlin.lora_multihop_logic.components.messages.JoinReplyMessage;
import de.htwberlin.lora_multihop_logic.components.messages.Message;
import de.htwberlin.lora_multihop_logic.components.parser.MessageParser;
import de.htwberlin.lora_multihop_logic.components.parser.ParserException;

import static junit.framework.TestCase.assertNull;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests JOIN_REPLY messages Parser
 * <p>
 * Test Cases:
 * <p>
 * - "JOIN_REPLY"
 * - if source Address is valid
 * - if latitude/longitude can be parsed to double
 * - missing / to much parts in message
 */
public class ParseJoinReplyMessagesTest {
    private MessageParser parser;

    @Before
    public void init() {
        parser = new MessageParser();
    }

    @Test
    public void givenValidJoinReplyMessageShouldReturnInstanceOfJoinReply() throws Exception {
        String inputMessage = "JOIN_REPLY;12DE;34.343;56.323";
        Message joinReplyMessage = parser.parseInput(inputMessage);
        assertThat(joinReplyMessage, instanceOf(JoinReplyMessage.class));
    }

    @Test
    public void givenValidJoinReplyMessageShouldReturnInstanceOfJoinReply2() throws Exception {
        String inputMessage = "JOIN_REPLY;F2DE;29.343;1.323";
        Message joinReplyMessage = parser.parseInput(inputMessage);
        assertThat(joinReplyMessage, instanceOf(JoinReplyMessage.class));
    }

    @Test
    public void givenValidJoinReplyMessageShouldReturnInstanceOfJoinReply3() throws Exception {
        String inputMessage = "JOIN_REPLY;1212;9.343;56.323";
        Message joinReplyMessage = parser.parseInput(inputMessage);
        assertThat(joinReplyMessage, instanceOf(JoinReplyMessage.class));
    }

    @Test
    public void givenInvalidJoinReplyMessageShouldNotBeAddedToQueue() throws Exception {
        String inputMessage = "JIN_REPLY;1234;43.453;23.2232";
        Message joinReplyMessage = parser.parseInput(inputMessage);
        assertNull(joinReplyMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidJoinReplyMessageShouldThrowParserException() throws Exception {
        String inputMessage = "JOIN_REPLY;1234;43.453;23.2232;0";
        parser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidJoinReplyMessageShouldThrowParserException2() throws Exception {
        String inputMessage = "JOIN_REPLY;1234;43.453";
        parser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidJoinReplyMessageShouldThrowParserException3() throws Exception {
        String inputMessage = "JOIN_REPLY;123y4;43.453;54.232";
        parser.parseInput(inputMessage);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidJoinReplyMessageShouldThrowNumberformatException() throws Exception {
        String inputMessage = "JOIN_REPLY;1234;43,453;54.232";
        parser.parseInput(inputMessage);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidJoinReplyMessageShouldThrowNumberformatException2() throws Exception {
        String inputMessage = "JOIN_REPLY;1234;43s453;54.232";
        parser.parseInput(inputMessage);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidJoinReplyMessageShouldThrowNumberformatException3() throws Exception {
        String inputMessage = "JOIN_REPLY;1234;43.453;54.232l";
        parser.parseInput(inputMessage);
    }
}
