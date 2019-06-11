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
 * Tests PUSH messages Parser
 *
 * Test Cases:
 *
 *  - "PUSH"
 *  - if source Address is valid
 *  - if latitude/longitude can be parsed to double
 *  - missing / to much parts in message
 *
 */
public class ParserPushMessagesTest {

    private IncomingMessageQueue queue;

    @Before
    public void init(){
        queue = IncomingMessageQueue.getInstance();
    }

    @Test
    public void givenValidPushMessageShouldReturnInstanceOfPushMessage() throws Exception {
        String inputMessage = "PUSH;1234;23DE;34DE;43.453;23.2232";
        MessageParser.parseInput(inputMessage);
        Message pushMessage = (Message) queue.poll();
        assertThat(pushMessage, instanceOf(PushMessage.class));
    }

    @Test
    public void givenValidPushMessageShouldReturnInstanceOfPushMessage2() throws Exception {
        String inputMessage = "PUSH;12DE;343E;1232;21.453;45.2232";
        MessageParser.parseInput(inputMessage);
        Message pushMessage = (Message) queue.poll();
        assertThat(pushMessage, instanceOf(PushMessage.class));
    }

    @Test
    public void givenValidPushMessageShouldReturnInstanceOfPushMessage3() throws Exception {
        String inputMessage = "PUSH;FFED;45DE;FF34;43.453;23.2232";
        MessageParser.parseInput(inputMessage);
        Message pushMessage = (Message) queue.poll();
        assertThat(pushMessage, instanceOf(PushMessage.class));
    }

    @Test
    public void givenInvalidPullMessageShouldNotBeAddedToQueue() throws Exception {
        int queueSizeBefore = queue.size();
        String inputMessage = "PLL;1234;2432;24DE;43.453;23.2232";
        MessageParser.parseInput(inputMessage);
        assertEquals(queueSizeBefore,0);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidPullMessageShouldThrowParserException() throws Exception {
        String inputMessage = "PULL;1234;DEAB;DEA3;43.453;23.2232;0";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidPullMessageShouldThrowParserException2() throws Exception {
        String inputMessage = "PULL;1234";
        MessageParser.parseInput(inputMessage);
    }

    @Test(expected = ParserException.class)
    public void givenInvalidPullMessageShouldThrowParserException3() throws Exception {
        String inputMessage = "PULL;123y4;43.453;54.232";
        MessageParser.parseInput(inputMessage);
    }

}
