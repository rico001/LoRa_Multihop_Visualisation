package de.htwberlin.lora_multihop_logic.components.processor.exchange;

import java.security.InvalidParameterException;
import java.util.Queue;

import de.htwberlin.lora_multihop_logic.components.messages.AckMessage;
import de.htwberlin.lora_multihop_logic.components.messages.FetchMessage;
import de.htwberlin.lora_multihop_logic.components.messages.FetchReplyMessage;
import de.htwberlin.lora_multihop_logic.components.messages.Message;

public class FetchExchangeHandler extends ExchangeHandler {
    public FetchExchangeHandler(Queue<Message> queue) {
        super(queue);
    }

    public FetchExchangeHandler(Queue<Message> queue, Message receivedMessage) {
        super(queue, receivedMessage);
    }

    @Override
    protected Message getStartMessage() {
        return null;
    }

    @Override
    public void processMessage(Message message) {
        if (message instanceof FetchMessage) {
            if (!this.isReplier()) {
                throw new InvalidParameterException("The current handler IS NOT replier, but invalid init FetchMessage received.");
            }
            handleInit(message);
        }
        if (message instanceof FetchReplyMessage) {
            if (this.isReplier()) {
                throw new InvalidParameterException("The current handler IS replier, but invalid reply FetchReplyMessage received.");
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
        // todo: figure out the payload.
        FetchReplyMessage replyMsg = new FetchReplyMessage(id, sourceAddress, remoteAddress, 0, null);
        getQueue().add(replyMsg);

//        LoRaApplication.getDb().neighbourSetDao().getAllNeighbourSets().size()
        // todo: what is the expected format of checksums?

    }

    @Override
    protected void handleReply(Message msg) {

    }

    @Override
    protected void handleAck(AckMessage msg) {

    }
}
