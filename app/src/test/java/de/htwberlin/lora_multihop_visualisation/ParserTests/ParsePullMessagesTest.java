package de.htwberlin.lora_multihop_visualisation.ParserTests;

import org.junit.Before;
import org.junit.Test;

import de.htwberlin.lora_multihop_implementation.components.messages.Message;
import de.htwberlin.lora_multihop_implementation.components.parser.MessageParser;
import de.htwberlin.lora_multihop_implementation.components.parser.ParserException;
import de.htwberlin.lora_multihop_implementation.components.queue.IncomingMessageQueue;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * Tests PULL messages Parser
 *
 * Test Cases:
 *
 *  - "PULL"
 *  - if source Address is valid
 *  - missing / to much parts in message
 *
 */

public class ParsePullMessagesTest {

    private IncomingMessageQueue queue;

    @Before
    public void init(){
        queue = IncomingMessageQueue.getInstance();
    }

    @Test
    public void givenValidPullMessageShouldReturnInstanceOfPullMessage() throws Exception {
        String inputMessage = "PULL;1234;34534";
        MessageParser.parseInput(inputMessage);
        Message pullMessage = (Message) queue.poll();
        assertThat(pullMessage, instanceOf(PullMessage.class));
    }

    @Test
    public void givenValidPullMessageShouldReturnInstanceOfPullMessage2() throws Exception {
        String inputMessage = "PULL;1234;34534";
        MessageParser.parseInput(inputMessage);
        Message pullMessage = (Message) queue.poll();
        assertThat(pullMessage, instanceOf(PullMessage.class));
    }

    @Test
    public void givenValidPullMessageShouldReturnInstanceOfPullMessage3() throws Exception {
        String inputMessage = "PULL;1234;34534";
        MessageParser.parseInput(inputMessage);
        Message pullMessage = (Message) queue.poll();
        assertThat(pullMessage, instanceOf(PullMessage.class));
    }

    @Test
    public void givenInvalidPullMessageShouldNotBeAddedToQueue() throws Exception {
        int queueSizeBefore = queue.size();
        String inputMessage = "PLL;1234;43453";
        MessageParser.parseInput(inputMessage);
        assertEquals(queueSizeBefore,0);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidPullMessageShouldThrowParserException() throws Exception {
        String inputMessage = "PULL;1234;43453;0";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidPullMessageShouldThrowParserException2() throws Exception {
        String inputMessage = "PULL;124;43.453";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidPullMessageShouldThrowParserException3() throws Exception {
        String inputMessage = "PULL;123y4;43.453;54.232";
        MessageParser.parseInput(inputMessage);
    }
}
