package com.prabhat.instrument.price.instrument.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prabhat.instrument.price.instrument.broker.ConsumerMessage;
import com.prabhat.instrument.price.instrument.producer.ProducerInstrument;
import com.prabhat.instrument.price.instrument.storage.Instrument;
import com.prabhat.instrument.price.instrument.storage.InstrumentPriceStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class InstrumentDataProcessor {
  private final ObjectMapper objectMapper;
  private final InstrumentPriceStorage instrumentPriceStorage;

  /**
   * Assuming the producer triggers the start with the total number of items to be created, the consumer can simply stop
   * working once all items have been finished. We need to have a timeout for this function so that failure of producers
   * would not cause unlimited waiting period for consumers.
   * <p>
   * Consumer is triggered by the producer.
   * <p>
   * Assuming the data is produced by the producer as files in a specified folder. The folder name is passed to the consumer
   * as parameter.
   * <p>
   * For real world scenario - We can realize this as a message stored in a message broker - say SQS. The message would
   * contain the destination information. For e.g. Producer produces data in a S3 bucket.
   */
  public void consumeDataTask(final ConsumerMessage consumerMessage) throws IOException, InterruptedException {
    // This sleep is required because consumer is too fast to start reading the
    Thread.sleep(100);

    log.info("Consumer task started {}", consumerMessage.toString());
    final File folder = new File(consumerMessage.getFolderPath());
    final Map<String, Boolean> filesDone = new HashMap<>();

    int filesCompleted = 0;
    final List<Instrument> consumerMessageList = new ArrayList<>();

    while (filesCompleted < consumerMessage.getTotalFiles()) {
      final File[] files = folder.listFiles();
      log.info("files  " + files.length);

      for (final File file : files) {
        if (filesDone.get(file.getName()) != Boolean.TRUE) {
          final ProducerInstrument producerInstrument = objectMapper.readValue(file, ProducerInstrument.class);
          consumerMessageList.add(this.toInstrument(producerInstrument));
          filesDone.put(file.getName(), Boolean.TRUE);
        }
      }

      log.info("files completed " + filesCompleted);
      // At this point, we are sure that the producer has completed the batch.
      filesCompleted += files.length;
    }
    log.info("All data consumed  " + filesCompleted);

    consumerMessageList.forEach(instrumentPriceStorage::addPrice);
  }

  private Instrument toInstrument(final ProducerInstrument producerInstrument) {
    return Instrument.builder()
        .id(producerInstrument.getId())
        .asOf(producerInstrument.getAsOf())
        .price(producerInstrument.getPrice())
        .build();
  }
}
