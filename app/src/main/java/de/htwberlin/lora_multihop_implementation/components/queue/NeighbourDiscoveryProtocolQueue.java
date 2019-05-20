package de.htwberlin.lora_multihop_implementation.components.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author morelly_t1
 */
public class NeighbourDiscoveryProtocolQueue extends ConcurrentLinkedQueue {

    public NeighbourDiscoveryProtocolQueue() {
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

}
