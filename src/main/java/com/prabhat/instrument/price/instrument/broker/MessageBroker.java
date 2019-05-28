package com.prabhat.instrument.price.instrument.broker;

import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
public class MessageBroker {
    private final BlockingQueue<ConsumerMessage> queue = new LinkedBlockingQueue<>(10);

    public void put(final ConsumerMessage consumerMessage) throws InterruptedException {
        queue.put(consumerMessage);
    }

    public synchronized ConsumerMessage get() throws InterruptedException {
        // TODO: Put the timeout in a config file.
        return queue.poll(10, TimeUnit.SECONDS);
    }
}
