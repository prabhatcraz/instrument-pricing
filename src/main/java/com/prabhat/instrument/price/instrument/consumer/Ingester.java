package com.prabhat.instrument.price.instrument.consumer;

import com.prabhat.instrument.price.instrument.broker.ConsumerMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
@Slf4j
public class Ingester {
    private final ConsumerConfig consumerConfig;
    private final InstrumentDataProcessor instrumentDataProcessor;

    public void consumeWithTimeout(final ConsumerMessage consumerMessage) {
        final ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(() -> {
            try {
                log.info("trying to consume");
                instrumentDataProcessor.consumeDataTask(consumerMessage);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e.getCause());
            }
        });

        executor.shutdown();
        try {
            log.info(String.format("Should kill thread in %s seconds", consumerConfig.getTaskTimeOut()));
            if (!executor.awaitTermination(consumerConfig.getTaskTimeOut(), TimeUnit.SECONDS)) {
                executor.shutdownNow();
                // We should be emitting a metric here so that we can monitor if a task was killed. This would help us
                // in understanding the behavior of producer as well as if something wrong is going on while consuming.
                log.info("Killed the thread after waiting.");

            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
