package com.prabhat.instrument.price.instrument.broker;

import com.prabhat.instrument.price.instrument.consumer.Consumer;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MessageBrokerTest {
    @Test
    public void testGetAndPut() {
        // GIVEN
        final MessageBroker messageBroker = new MessageBroker();
        final String id = UUID.randomUUID().toString();

        // WHEN
        messageBroker.put(ConsumerMessage.builder()
                .totalFiles(100)
                .folderPath(id)
                .build());
        final ConsumerMessage message = messageBroker.get();
        // THEN
        assertEquals(id, message.getFolderPath());
        assertEquals(100, message.getTotalFiles());
    }

    @Test
    public void shouldNotGetSameMessageAgain() {
        // GIVEN
        final MessageBroker messageBroker = new MessageBroker();
        final String id = UUID.randomUUID().toString();

        // WHEN
        messageBroker.put(ConsumerMessage.builder()
                .totalFiles(100)
                .folderPath(id)
                .build());

        final ConsumerMessage message = messageBroker.get();
        final ConsumerMessage message1 = messageBroker.get();
        assertNull(message1);

        // THEN
    }
}