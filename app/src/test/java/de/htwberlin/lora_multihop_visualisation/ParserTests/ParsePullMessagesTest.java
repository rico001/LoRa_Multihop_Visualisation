package de.htwberlin.lora_multihop_visualisation.ParserTests;

import org.junit.Before;
import org.junit.Test;

import de.htwberlin.lora_multihop_logic.components.messages.Message;
import de.htwberlin.lora_multihop_logic.components.messages.PullMessage;
import de.htwberlin.lora_multihop_logic.components.parser.MessageParser;
import de.htwberlin.lora_multihop_logic.components.parser.ParserException;

import static junit.framework.TestCase.assertNull;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests PULL messages Parser
 * <p>
 * Test Cases:
 * <p>
 * - "PULL"
 * - if source Address is valid
 * - missing / to much parts in message
 */

public class ParsePullMessagesTest {
    private MessageParser parser;

    @Before
    public void init() {
        parser = new MessageParser();
    }

    @Test
    public void givenValidPullMessageShouldReturnInstanceOfPullMessage() throws Exception {
        String inputMessage = "PULL;1234;34534";
        Message pullMessage = parser.parseInput(inputMessage);
        assertThat(pullMessage, instanceOf(PullMessage.class));
    }

    @Test
    public void givenValidPullMessageShouldReturnInstanceOfPullMessage2() throws Exception {
        String inputMessage = "PULL;1234;34534";
        Message pullMessage = parser.parseInput(inputMessage);
        assertThat(pullMessage, instanceOf(PullMessage.class));
    }

    @Test
    public void givenValidPullMessageShouldReturnInstanceOfPullMessage3() throws Exception {
        String inputMessage = "PULL;1234;34534";
        Message pullMessage = parser.parseInput(inputMessage);
        assertThat(pullMessage, instanceOf(PullMessage.class));
    }

    @Test
    public void givenInvalidPullMessageShouldNotBeAddedToQueue() throws Exception {
        String inputMessage = "PLL;1234;43453";
        Message pullMessage = parser.parseInput(inputMessage);
        assertNull(pullMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidPullMessageShouldThrowParserException() throws Exception {
        String inputMessage = "PULL;1234;43453;0";
        parser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidPullMessageShouldThrowParserException2() throws Exception {
        String inputMessage = "PULL;124;43.453";
        parser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidPullMessageShouldThrowParserException3() throws Exception {
        String inputMessage = "PULL;123y4;43.453;54.232";
        parser.parseInput(inputMessage);
    }
}
