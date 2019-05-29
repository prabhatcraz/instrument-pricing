package com.prabhat.instrument.price.instrument.consumer;

import com.prabhat.instrument.price.instrument.broker.ConsumerMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.apache.naming.SelectorContext.prefix;

@RunWith(MockitoJUnitRunner.class)
public class IngesterTest {
    @Mock
    private InstrumentDataProcessor instrumentDataProcessor;
    @Test
    public void threadShouldBeKilledWithinSpecifiedTime() throws IOException {
        // GIVEN
        final Path tempDirWithPrefix = Files.createTempDirectory(prefix);
        final ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTaskTimeOut(5);

        // WHEN
        final Ingester ingester = new Ingester(consumerConfig, instrumentDataProcessor);
        long now = System.currentTimeMillis();
        ingester.consumeWithTimeout(ConsumerMessage.builder()
                .folderPath(tempDirWithPrefix.toString())
                .totalFiles(100)
                .build());

        // THEN
        assert (System.currentTimeMillis() - now) < 6000;
    }
}