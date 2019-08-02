package de.htwberlin.lora_multihop_logic.components.processor.exchange;

import java.util.Queue;
import java.util.UUID;

import de.htwberlin.lora_multihop_logic.components.lora.LoraHandler;
import de.htwberlin.lora_multihop_logic.components.messages.AckMessage;
import de.htwberlin.lora_multihop_logic.components.messages.Message;

abstract public class ExchangeHandler {
    protected String id;
    protected String remoteAddress;
    protected String sourceAddress;

    private Message initMessage;
    private Message replyMessage;
    /**
     * Does the current device reply to a message? False if current device initialized a new exchange.
     */
    private boolean isReplier;
    /**
     * Set to the current timestamp ms when the exchange is initiated.
     * Used to determine when an exchange didn't receive a response and is timed-out.
     */
    private long startedTimestamp;
    /**
     * If the ACK message was received. Means the exchange has ended successfully.
     */
    private boolean acknoweldged = false;

    private Queue<Message> queue;

    public ExchangeHandler(Queue<Message> queue) {
        this.queue = queue;
        this.id = generateId();
        this.startedTimestamp = System.currentTimeMillis();
        this.isReplier = false;
        this.sourceAddress = LoraHandler.getSourceAddress();
    }

    public ExchangeHandler(Queue<Message> queue, Message receivedMessage) {
        this.queue = queue;
        this.id = receivedMessage.getId();
        this.startedTimestamp = System.currentTimeMillis();
        this.isReplier = true;
        this.sourceAddress = LoraHandler.getSourceAddress();

        this.initMessage = receivedMessage;
    }

    /**
     * Generate a random ID of 5 symbols.
     * todo: maybe shorter or longer id. maybe use the whole UUID as it also includes the timestamp
     */
    protected String generateId() {
        return UUID.randomUUID().toString().substring(0, 5);
    }

    public String getId() {
        return id;
    }

    public void sendInitMessage() {
        this.initMessage = getStartMessage();

        queue.add(this.initMessage);
    }

    public boolean isReplier() {
        return isReplier;
    }

    public long getStartedTimestamp() {
        return startedTimestamp;
    }

    public boolean isAcknoweldged() {
        return acknoweldged;
    }

    public Queue getQueue() {
        return this.queue;
    }

    abstract protected Message getStartMessage();

    /**
     * When a new message is received, process it to do the corresponding job.
     *
     * @param message
     * @return
     */
    abstract public void processMessage(Message message);

    abstract protected void handleInit(Message msg);

    abstract protected void handleReply(Message msg);

    abstract protected void handleAck(AckMessage msg);
}
