package de.htwberlin.lora_multihop_implementation.components.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

import de.htwberlin.lora_multihop_implementation.components.messages.Message;

/**
 * @author morelly_t1
 */
public class NeighbourDiscoveryProtocolQueue extends ConcurrentLinkedQueue {

    public NeighbourDiscoveryProtocolQueue() {
        super();
    }

    @Override
    public boolean add(Message o) {
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

}
