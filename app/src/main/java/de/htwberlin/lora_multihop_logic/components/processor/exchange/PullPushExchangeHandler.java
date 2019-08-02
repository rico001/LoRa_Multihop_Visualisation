package de.htwberlin.lora_multihop_logic.components.processor.exchange;

import java.security.InvalidParameterException;
import java.util.Queue;

import de.htwberlin.lora_multihop_logic.components.messages.AckMessage;
import de.htwberlin.lora_multihop_logic.components.messages.Message;
import de.htwberlin.lora_multihop_logic.components.messages.PullMessage;
import de.htwberlin.lora_multihop_logic.components.messages.PushMessage;

public class PullPushExchangeHandler extends ExchangeHandler {
    public PullPushExchangeHandler(Queue<Message> queue) {
        super(queue);
    }

    public PullPushExchangeHandler(Queue<Message> queue, Message receivedMessage) {
        super(queue, receivedMessage);
    }

    @Override
    protected Message getStartMessage() {
        return null;
    }

    @Override
    public void processMessage(Message message) {
        if (message instanceof PullMessage) {
            if (!this.isReplier()) {
                throw new InvalidParameterException("The current handler IS NOT replier, but invalid init PullMessage received.");
            }
            handleInit(message);
        }
        if (message instanceof PushMessage) {
            if (this.isReplier()) {
                throw new InvalidParameterException("The current handler IS replier, but invalid reply PushMessage received.");
            }
            handleReply(message);
        }
        if (message instanceof AckMessage) {
            if (!this.isReplier()) {
                throw new InvalidParameterException("The current handler IS NOT replier, but invalid AckMessage received.");
            }
            handleAck((AckMessage) message);
        }

        System.out.println("Received message " + message.getClass()
                .toString() + message.toString());
    }

    @Override
    protected void handleInit(Message msg) {

    }

    @Override
    protected void handleReply(Message msg) {

    }

    @Override
    protected void handleAck(AckMessage msg) {

    }
}
