package de.htwberlin.lora_multihop_visualisation.ParserTests;

import org.junit.Before;
import org.junit.Test;

import de.htwberlin.lora_multihop_logic.components.messages.JoinMessage;
import de.htwberlin.lora_multihop_logic.components.messages.Message;
import de.htwberlin.lora_multihop_logic.components.parser.MessageParser;
import de.htwberlin.lora_multihop_logic.components.parser.ParserException;

import static junit.framework.TestCase.assertNull;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests JOIN messages Parser
 * <p>
 * Test Cases:
 * <p>
 * - "JOIN"
 * - if source Address is valid
 * - if latitude/longitude can be parsed to double
 * - missing / to much parts in message
 */
public class ParseJoinMessagesTest {
    private MessageParser parser;

    @Before
    public void init() {
        parser = new MessageParser();
    }

    @Test
    public void givenValidJoinMessageShouldReturnInstanceOfJoinMessage() throws Exception {
        String inputMessage = "JOIN;1234;43.453;23.2232";
        Message joinMessage = parser.parseInput(inputMessage);
        assertThat(joinMessage, instanceOf(JoinMessage.class));
    }

    @Test
    public void givenValidJoinMessageShouldReturnInstanceOfJoinMessage2() throws Exception {
        String inputMessage = "JOIN;12DE;21.453;45.2232";
        Message joinMessage = parser.parseInput(inputMessage);
        assertThat(joinMessage, instanceOf(JoinMessage.class));
    }

    @Test
    public void givenValidJoinMessageShouldReturnInstanceOfJoinMessage3() throws Exception {
        String inputMessage = "JOIN;FFED;43.453;23.2232";
        Message joinMessage = parser.parseInput(inputMessage);
        assertThat(joinMessage, instanceOf(JoinMessage.class));
    }

    @Test
    public void givenInvalidJoinMessageShouldNotBeAddedToQueue() throws Exception {
        String inputMessage = "JIN;1234;43.453;23.2232";
        Message joinMessage = parser.parseInput(inputMessage);
        assertNull(joinMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidJoinMessageShouldThrowParserException() throws Exception {
        String inputMessage = "JOIN;1234;43.453;23.2232;0";
        Message joinMessage = parser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidJoinMessageShouldThrowParserException2() throws Exception {
        String inputMessage = "JOIN;1234;43.453";
        Message joinMessage = parser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidJoinMessageShouldThrowParserException3() throws Exception {
        String inputMessage = "JOIN;123y4;43.453;54.232";
        parser.parseInput(inputMessage);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidJoinMessageShouldThrowNumberformatException() throws Exception {
        String inputMessage = "JOIN;1234;43,453;54.232";
        parser.parseInput(inputMessage);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidJoinMessageShouldThrowNumberformatException2() throws Exception {
        String inputMessage = "JOIN;1234;43s453;54.232";
        parser.parseInput(inputMessage);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidJoinMessageShouldThrowNumberformatException3() throws Exception {
        String inputMessage = "JOIN;1234;43.453;54.232l";
        parser.parseInput(inputMessage);
    }
}
