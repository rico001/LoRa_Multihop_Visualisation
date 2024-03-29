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
 * Tests MOVE messages Parser
 * <p>
 * Test Cases:
 * <p>
 * - "MOVE"
 * - if source Address is valid
 * - if latitude/longitude can be parsed to double
 * - missing / to much parts in message
 */

public class ParserMoveMessagesTest {
    private MessageParser parser;

    @Before
    public void init() {
        parser = new MessageParser();
    }

    @Test
    public void givenValidMoveMessageShouldReturnInstanceOfMoveMessage() throws Exception {
        String inputMessage = "MOVE;1234;43.453;23.2232";
        Message moveMessage = parser.parseInput(inputMessage);
        assertThat(moveMessage, instanceOf(MoveMessage.class));
    }

    @Test
    public void givenValidMoveMessageShouldReturnInstanceOfMoveMessage2() throws Exception {
        String inputMessage = "MOVE;12DE;21.453;45.2232";
        Message moveMessage = parser.parseInput(inputMessage);
        assertThat(moveMessage, instanceOf(MoveMessage.class));
    }

    @Test
    public void givenValidMoveMessageShouldReturnInstanceOfMoveMessage3() throws Exception {
        String inputMessage = "MOVE;FFED;43.453;23.2232";
        Message moveMessage = parser.parseInput(inputMessage);
        assertThat(moveMessage, instanceOf(MoveMessage.class));
    }

    @Test
    public void givenInvalidMoveMessageShouldNotBeAddedToQueue() throws Exception {
        String inputMessage = "MVE;1234;43.453;23.2232";
        Message moveMessage = parser.parseInput(inputMessage);
        assertNull(moveMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidMoveMessageShouldThrowParserException() throws Exception {
        String inputMessage = "MOVE;1234;43.453;23.2232;0";
        parser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidMoveMessageShouldThrowParserException2() throws Exception {
        String inputMessage = "MOVE;1234;43.453";
        parser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidMoveMessageShouldThrowParserException3() throws Exception {
        String inputMessage = "MOVE;123y4;43.453;54.232";
        parser.parseInput(inputMessage);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidMoveMessageShouldThrowNumberformatException() throws Exception {
        String inputMessage = "MOVE;1234;43,453;54.232";
        parser.parseInput(inputMessage);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidMoveMessageShouldThrowNumberformatException2() throws Exception {
        String inputMessage = "MOVE;1234;43s453;54.232";
        parser.parseInput(inputMessage);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInvalidMoveMessageShouldThrowNumberformatException3() throws Exception {
        String inputMessage = "MOVE;1234;43.453;54.232l";
        parser.parseInput(inputMessage);
    }
}
