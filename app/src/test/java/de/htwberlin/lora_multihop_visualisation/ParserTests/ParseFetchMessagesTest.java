package de.htwberlin.lora_multihop_visualisation.ParserTests;

import org.junit.Before;
import org.junit.Test;

import de.htwberlin.lora_multihop_logic.components.messages.FetchMessage;
import de.htwberlin.lora_multihop_logic.components.messages.Message;
import de.htwberlin.lora_multihop_logic.components.parser.MessageParser;
import de.htwberlin.lora_multihop_logic.components.parser.ParserException;

import static junit.framework.TestCase.assertNull;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests FETCH messages Parser
 * <p>
 * Test Cases:
 * <p>
 * - "JOIN_REPLY"
 * - if source Address is valid
 * - missing / to much parts in message
 */
public class ParseFetchMessagesTest {
    private MessageParser parser;

    @Before
    public void init() {
        parser = new MessageParser();
    }

    @Test
    public void givenValidFetchMessageShouldReturnInstanceOfFetch() throws Exception {
        String inputMessage = "FETCH;12DE";
        Message fetchMessage = parser.parseInput(inputMessage);
        assertThat(fetchMessage, instanceOf(FetchMessage.class));
    }

    @Test
    public void givenValidFetchMessageShouldReturnInstanceOfFetch2() throws Exception {
        String inputMessage = "FETCH;0ABC";
        Message fetchMessage = parser.parseInput(inputMessage);
        assertThat(fetchMessage, instanceOf(FetchMessage.class));
    }

    @Test
    public void givenValidFetchMessageShouldReturnInstanceOfFetch3() throws Exception {
        String inputMessage = "FETCH;FFFE";
        Message fetchMessage = parser.parseInput(inputMessage);
        assertThat(fetchMessage, instanceOf(FetchMessage.class));
    }

    @Test
    public void givenInvalidFetchMessageShouldNotBeAddedToQueue() throws Exception {
        String inputMessage = "FECH";
        Message fetchMessage = parser.parseInput(inputMessage);
        assertNull(fetchMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidFetchMessageShouldThrowParserException() throws Exception {
        String inputMessage = "FETCH;12DE;0";
        Message fetchMessage = parser.parseInput(inputMessage);
    }
}
