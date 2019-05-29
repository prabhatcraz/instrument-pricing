package com.prabhat.instrument.price.instrument.broker;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A very simple implementation of a message broker to communicate start of producing data between producer and the consumer.
 */
@Component
public class MessageBroker {
    /**
     * Although there is only one thread of consumer right now, we can easily create multiple consumers.
     */
    private final ConcurrentLinkedQueue<ConsumerMessage> queue = new ConcurrentLinkedQueue<>();

    public void put(final ConsumerMessage consumerMessage) {
        queue.add(consumerMessage);
    }

    public synchronized ConsumerMessage get() {
        // TODO: Put the timeout in a config file.
        return queue.poll();
    }
}
