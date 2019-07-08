package de.htwberlin.lora_multihop_logic.components.processor;

import java.util.Queue;
import java.util.UUID;

import de.htwberlin.lora_multihop_logic.NeighbourSetDataHandler;
import de.htwberlin.lora_multihop_logic.components.messages.AckMessage;
import de.htwberlin.lora_multihop_logic.components.messages.Message;

abstract public class ExchangeHandler {

    protected String id;

    private String remoteAddress;
    private String sourceAddress;

    private Message initMessage;
    private Message replyMessage;

    private NeighbourSetDataHandler neighbourSetDataHandler;

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

    ExchangeHandler(Queue<Message> queue, NeighbourSetDataHandler neighbourSetDataHandler) {
        this.queue = queue;
        this.id = generateId();
        this.startedTimestamp = System.currentTimeMillis();
        this.isReplier = false;

        this.neighbourSetDataHandler = neighbourSetDataHandler;

        sendInitMessage();
    }

    ExchangeHandler(Queue<Message> queue, Message receivedMessage, NeighbourSetDataHandler neighbourSetDataHandler) {
        this.queue = queue;
        this.id = receivedMessage.getId();
        this.startedTimestamp = System.currentTimeMillis();
        this.isReplier = true;

        this.neighbourSetDataHandler = neighbourSetDataHandler;

        this.initMessage = receivedMessage;
    }

    /**
     * Generate a random ID of 5 symbols.
     * todo: maybe shorter or longer id. maybe use the whole UUID as it also includes the timestamp
     */
    protected String generateId() {
        return UUID.randomUUID().toString().substring(0, 5);
    }

    protected void sendInitMessage() {
        this.initMessage = getStartMessage();

        queue.add(this.initMessage);
    }

    /**
     * Do the message work (add coordinates to the db or whatever) and schedule a new reply message if needed.
     */
    public void receiveMessage(Message receivedMessage) {
        // todo: for broadcast messages we probably need to keep processing new messages - for all responses we get
        if (this.acknoweldged) {
            throw new IllegalStateException("The current messages exchange is already acknowledged, i.e. finished. Can't receive new messages.");
        }

        // Do the corresponding message work. For example for received Join or JoinReply - store the coordinates.
        this.processMessage(receivedMessage);

        this.acknoweldged = receivedMessage instanceof AckMessage;
        if (!this.acknoweldged) {
            this.replyMessage = this.getReplyMessage();
            queue.add(replyMessage);
        }

        // debug
        if (this.acknoweldged) {
            System.out.println("Messages exchange " + this.id + " is acknoledged, i.e. finished");
        }
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

    abstract protected Message getReplyMessage();

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public NeighbourSetDataHandler getNeighbourSetDataHandler() {
        return neighbourSetDataHandler;
    }

    public void setNeighbourSetDataHandler(NeighbourSetDataHandler neighbourSetDataHandler) {
        this.neighbourSetDataHandler = neighbourSetDataHandler;
    }

    /**
     * When a new message is received, process it to do the corresponding job.
     *
     * @param message
     * @return
     */
    abstract protected boolean processMessage(Message message);

}
