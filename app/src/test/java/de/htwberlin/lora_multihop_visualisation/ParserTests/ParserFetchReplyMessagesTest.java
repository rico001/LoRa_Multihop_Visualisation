package de.htwberlin.lora_multihop_visualisation.ParserTests;

import org.junit.Before;
import org.junit.Test;

import de.htwberlin.lora_multihop_logic.components.messages.FetchReplyMessage;
import de.htwberlin.lora_multihop_logic.components.messages.Message;
import de.htwberlin.lora_multihop_logic.components.parser.MessageParser;
import de.htwberlin.lora_multihop_logic.components.parser.ParserException;

import static junit.framework.TestCase.assertNull;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests FETCH_REPLY messages Parser
 * <p>
 * Test Cases:
 * <p>
 * - "FETCH_REPLY"
 * - if source Address is valid
 * - if latitude/longitude can be parsed to double
 * - missing / to much parts in message
 */
public class ParserFetchReplyMessagesTest {
    private MessageParser parser;

    @Before
    public void init() {
        parser = new MessageParser();
    }

    @Test
    public void givenValidFetchReplyMessageShouldReturnInstanceOfFetch() throws Exception {
        String inputMessage = "FETCH_REPLY;12DE;1;34541";
        Message fetchReplyMessage = parser.parseInput(inputMessage);
        assertThat(fetchReplyMessage, instanceOf(FetchReplyMessage.class));
    }

    @Test
    public void givenValidFetchReplyMessageShouldReturnInstanceOfFetch2() throws Exception {
        String inputMessage = "FETCH_REPLY;1209;3;34541;23232;12221";
        Message fetchReplyMessage = parser.parseInput(inputMessage);
        assertThat(fetchReplyMessage, instanceOf(FetchReplyMessage.class));
    }

    @Test
    public void givenValidFetchReplyMessageShouldReturnInstanceOfFetch3() throws Exception {
        String inputMessage = "FETCH_REPLY;12DE;4;34541;23233;23213;3223";
        Message fetchReplyMessage = parser.parseInput(inputMessage);
        assertThat(fetchReplyMessage, instanceOf(FetchReplyMessage.class));
    }

    @Test
    public void givenInvalidFetchReplyMessageShouldNotBeAddedToQueue() throws Exception {
        String inputMessage = "FETCHREPLY;12DE;4;34541;23233;23213;3223";
        Message fetchReplyMessage = parser.parseInput(inputMessage);
        assertNull(fetchReplyMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidFetchReplyMessageShouldThrowParserException() throws Exception {
        String inputMessage = "FETCH_REPLY;12DE;2;1323";
        parser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidFetchReplyMessageShouldThrowParserException2() throws Exception {
        String inputMessage = "FETCH_REPLY;12DE;2;";
        parser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidFetchReplyMessageShouldThrowParserException3() throws Exception {
        String inputMessage = "FETCH_REPLY;12DE;2;232;23432;243243;242334";
        parser.parseInput(inputMessage);
    }
}
