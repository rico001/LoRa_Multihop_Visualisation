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
 * Tests FETCH messages Parser
 *
 * Test Cases:
 *
 *  - "JOIN_REPLY"
 *  - if source Address is valid
 *  - missing / to much parts in message
 *
 */
public class ParseFetchMessagesTest {

    private IncomingMessageQueue queue;

    @Before
    public void init(){
        queue = IncomingMessageQueue.getInstance();
    }

    @Test
    public void givenValidFetchMessageShouldReturnInstanceOfFetch() throws Exception{
        String inputMessage = "FETCH;12DE";
        MessageParser.parseInput(inputMessage);
        Message fetchMessage = (Message) queue.poll();
        assertThat(fetchMessage, instanceOf(FetchMessage.class));
    }

    @Test
    public void givenValidFetchMessageShouldReturnInstanceOfFetch2() throws Exception{
        String inputMessage = "FETCH;0ABC";
        MessageParser.parseInput(inputMessage);
        Message fetchMessage = (Message) queue.poll();
        assertThat(fetchMessage, instanceOf(FetchMessage.class));
    }

    @Test
    public void givenValidFetchMessageShouldReturnInstanceOfFetch3() throws Exception{
        String inputMessage = "FETCH;FFFE";
        MessageParser.parseInput(inputMessage);
        Message fetchMessage = (Message) queue.poll();
        assertThat(fetchMessage, instanceOf(FetchMessage.class));
    }

    @Test
    public void givenInvalidFetchMessageShouldNotBeAddedToQueue() throws Exception {
        int queueSizeBefore = queue.size();
        String inputMessage = "FECH";
        MessageParser.parseInput(inputMessage);
        assertEquals(queueSizeBefore,0);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidFetchMessageShouldThrowParserException() throws Exception {
        String inputMessage = "FETCH;12DE;0";
        MessageParser.parseInput(inputMessage);
    }

}
