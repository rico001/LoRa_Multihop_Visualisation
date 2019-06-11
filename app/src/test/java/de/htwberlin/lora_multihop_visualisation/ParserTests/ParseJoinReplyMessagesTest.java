package de.htwberlin.lora_multihop_visualisation.ParserTests;

import org.junit.Before;
import org.junit.Test;

import de.htwberlin.lora_multihop_logic.components.messages.JoinReplyMessage;
import de.htwberlin.lora_multihop_logic.components.messages.Message;
import de.htwberlin.lora_multihop_logic.components.parser.MessageParser;
import de.htwberlin.lora_multihop_logic.components.parser.ParserException;
import de.htwberlin.lora_multihop_logic.components.queue.IncomingMessageQueue;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * Tests JOIN_REPLY messages Parser
 *
 * Test Cases:
 *
 *  - "JOIN_REPLY"
 *  - if source Address is valid
 *  - if latitude/longitude can be parsed to double
 *  - missing / to much parts in message
 *
 */
public class ParseJoinReplyMessagesTest {

    private IncomingMessageQueue queue;

    @Before
    public void init(){
        queue = IncomingMessageQueue.getInstance();
    }

    @Test
    public void givenValidJoinReplyMessageShouldReturnInstanceOfJoinReply() throws Exception{
        String inputMessage = "JOIN_REPLY;12DE;34.343;56.323";
        MessageParser.parseInput(inputMessage);
        Message joinReplyMessage = (Message) queue.poll();
        assertThat(joinReplyMessage, instanceOf(JoinReplyMessage.class));
    }

    @Test
    public void givenValidJoinReplyMessageShouldReturnInstanceOfJoinReply2() throws Exception{
        String inputMessage = "JOIN_REPLY;F2DE;29.343;1.323";
        MessageParser.parseInput(inputMessage);
        Message joinReplyMessage = (Message) queue.poll();
        assertThat(joinReplyMessage, instanceOf(JoinReplyMessage.class));
    }

    @Test
    public void givenValidJoinReplyMessageShouldReturnInstanceOfJoinReply3() throws Exception{
        String inputMessage = "JOIN_REPLY;1212;9.343;56.323";
        MessageParser.parseInput(inputMessage);
        Message joinReplyMessage = (Message) queue.poll();
        assertThat(joinReplyMessage, instanceOf(JoinReplyMessage.class));
    }

    @Test
    public void givenInvalidJoinReplyMessageShouldNotBeAddedToQueue() throws Exception {
        int queueSizeBefore = queue.size();
        String inputMessage = "JIN_REPLY;1234;43.453;23.2232";
        MessageParser.parseInput(inputMessage);
        assertEquals(queueSizeBefore,0);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidJoinReplyMessageShouldThrowParserException() throws Exception {
        String inputMessage = "JOIN_REPLY;1234;43.453;23.2232;0";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidJoinReplyMessageShouldThrowParserException2() throws Exception {
        String inputMessage = "JOIN_REPLY;1234;43.453";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidJoinReplyMessageShouldThrowParserException3() throws Exception {
        String inputMessage = "JOIN_REPLY;123y4;43.453;54.232";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidJoinReplyMessageShouldThrowNumberformatException() throws Exception {
        String inputMessage = "JOIN_REPLY;1234;43,453;54.232";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidJoinReplyMessageShouldThrowNumberformatException2() throws Exception {
        String inputMessage = "JOIN_REPLY;1234;43s453;54.232";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidJoinReplyMessageShouldThrowNumberformatException3() throws Exception {
        String inputMessage = "JOIN_REPLY;1234;43.453;54.232l";
        MessageParser.parseInput(inputMessage);
    }
}
