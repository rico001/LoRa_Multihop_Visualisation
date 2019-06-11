package de.htwberlin.lora_multihop_visualisation.ParserTests;

import org.junit.Before;
import org.junit.Test;

import de.htwberlin.lora_multihop_logic.components.messages.Message;
import de.htwberlin.lora_multihop_logic.components.parser.MessageParser;
import de.htwberlin.lora_multihop_logic.components.parser.ParserException;
import de.htwberlin.lora_multihop_logic.components.queue.IncomingMessageQueue;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * Tests FETCH_REPLY messages Parser
 *
 * Test Cases:
 *
 *  - "FETCH_REPLY"
 *  - if source Address is valid
 *  - if latitude/longitude can be parsed to double
 *  - missing / to much parts in message
 *
 */
public class ParserFetchReplyMessagesTest {

    private IncomingMessageQueue queue;

    @Before
    public void init(){
        queue = IncomingMessageQueue.getInstance();
    }

    @Test
    public void givenValidFetchReplyMessageShouldReturnInstanceOfFetch() throws Exception{
        String inputMessage = "FETCH_REPLY;12DE;1;34541";
        MessageParser.parseInput(inputMessage);
        Message fetchReplyMessage = (Message) queue.poll();
        assertThat(fetchReplyMessage, instanceOf(FetchReplyMessage.class));
    }

    @Test
    public void givenValidFetchReplyMessageShouldReturnInstanceOfFetch2() throws Exception{
        String inputMessage = "FETCH_REPLY;1209;3;34541;23232;12221";
        MessageParser.parseInput(inputMessage);
        Message fetchReplyMessage = (Message) queue.poll();
        assertThat(fetchReplyMessage, instanceOf(FetchReplyMessage.class));
    }

    @Test
    public void givenValidFetchReplyMessageShouldReturnInstanceOfFetch3() throws Exception{
        String inputMessage = "FETCH_REPLY;12DE;4;34541;23233;23213;3223";
        MessageParser.parseInput(inputMessage);
        Message fetchReplyMessage = (Message) queue.poll();
        assertThat(fetchReplyMessage, instanceOf(FetchReplyMessage.class));
    }

    @Test
    public void givenInvalidFetchReplyMessageShouldNotBeAddedToQueue() throws Exception{
        int queueSizeBefore = queue.size();
        String inputMessage = "FETCHREPLY;12DE;4;34541;23233;23213;3223";
        MessageParser.parseInput(inputMessage);
        assertEquals(queueSizeBefore,0);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidFetchReplyMessageShouldThrowParserException() throws Exception {
        String inputMessage = "FETCH_REPLY;12DE;2;1323";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidFetchReplyMessageShouldThrowParserException2() throws Exception {
        String inputMessage = "FETCH_REPLY;12DE;2;";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidFetchReplyMessageShouldThrowParserException3() throws Exception {
        String inputMessage = "FETCH_REPLY;12DE;2;232;23432;243243;242334";
        MessageParser.parseInput(inputMessage);
    }
}
