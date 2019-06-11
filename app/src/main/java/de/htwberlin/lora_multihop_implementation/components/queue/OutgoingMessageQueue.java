package de.htwberlin.lora_multihop_implementation.components.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Queue for Sending Messages to remote Hops
 * Is a Singleton
 *
 * @author morelly_t1
 */
public class OutgoingMessageQueue extends ConcurrentLinkedQueue {

    public static OutgoingMessageQueue queue = null;

    private OutgoingMessageQueue() {
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

    public static OutgoingMessageQueue getInstance() {
        if (queue == null) queue = new OutgoingMessageQueue();
        return queue;
    }
}
