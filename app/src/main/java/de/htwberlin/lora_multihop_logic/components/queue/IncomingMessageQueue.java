package de.htwberlin.lora_multihop_logic.components.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Queue for incoming messages
 * Is a Singleton
 *
 * @author morelly_t1
 */
public class IncomingMessageQueue extends ConcurrentLinkedQueue {

    public static IncomingMessageQueue queue = null;

    private IncomingMessageQueue() {
        super();
    }

    @Override
    public boolean add(Object o) {
        return super.add(o);
    }

    @Override
    public Object poll() {
        return super.poll();
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    public static IncomingMessageQueue getInstance() {
        if (queue == null) queue = new IncomingMessageQueue();
        return queue;
    }
}
