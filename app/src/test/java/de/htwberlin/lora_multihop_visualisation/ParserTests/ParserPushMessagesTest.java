package de.htwberlin.lora_multihop_visualisation.ParserTests;

import org.junit.Before;
import org.junit.Test;

import de.htwberlin.lora_multihop_logic.components.messages.Message;
import de.htwberlin.lora_multihop_logic.components.messages.PushMessage;
import de.htwberlin.lora_multihop_logic.components.parser.MessageParser;
import de.htwberlin.lora_multihop_logic.components.parser.ParserException;

import static junit.framework.TestCase.assertNull;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests PUSH messages Parser
 * <p>
 * Test Cases:
 * <p>
 * - "PUSH"
 * - if source Address is valid
 * - if latitude/longitude can be parsed to double
 * - missing / to much parts in message
 */
public class ParserPushMessagesTest {
    private MessageParser parser;

    @Before
    public void init() {
        parser = new MessageParser();
    }

    @Test
    public void givenValidPushMessageShouldReturnInstanceOfPushMessage() throws Exception {
        String inputMessage = "PUSH;1234;23DE;34DE;43.453;23.2232";
        Message pushMessage = parser.parseInput(inputMessage);
        assertThat(pushMessage, instanceOf(PushMessage.class));
    }

    @Test
    public void givenValidPushMessageShouldReturnInstanceOfPushMessage2() throws Exception {
        String inputMessage = "PUSH;12DE;343E;1232;21.453;45.2232";
        Message pushMessage = parser.parseInput(inputMessage);
        assertThat(pushMessage, instanceOf(PushMessage.class));
    }

    @Test
    public void givenValidPushMessageShouldReturnInstanceOfPushMessage3() throws Exception {
        String inputMessage = "PUSH;FFED;45DE;FF34;43.453;23.2232";
        Message pushMessage = parser.parseInput(inputMessage);
        assertThat(pushMessage, instanceOf(PushMessage.class));
    }

    @Test
    public void givenInvalidPullMessageShouldNotBeAddedToQueue() throws Exception {
        String inputMessage = "PLL;1234;2432;24DE;43.453;23.2232";
        Message pushMessage = parser.parseInput(inputMessage);
        assertNull(pushMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidPullMessageShouldThrowParserException() throws Exception {
        String inputMessage = "PULL;1234;DEAB;DEA3;43.453;23.2232;0";
        parser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidPullMessageShouldThrowParserException2() throws Exception {
        String inputMessage = "PULL;1234";
        parser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidPullMessageShouldThrowParserException3() throws Exception {
        String inputMessage = "PULL;123y4;43.453;54.232";
        parser.parseInput(inputMessage);
    }
}
