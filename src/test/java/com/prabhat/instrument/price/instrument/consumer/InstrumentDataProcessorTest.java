package com.prabhat.instrument.price.instrument.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.prabhat.instrument.price.instrument.broker.ConsumerMessage;
import com.prabhat.instrument.price.instrument.producer.ProducerInstrument;
import com.prabhat.instrument.price.instrument.storage.InMemoryInstrumentPriceStorage;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.UUID;

import static org.apache.naming.SelectorContext.prefix;
import static org.assertj.core.api.Assertions.assertThat;

public class InstrumentDataProcessorTest {
  @Test
  public void shouldSuccessFullyConsumeDataFromFile() throws IOException, InterruptedException {
    // GIVEN
    final Path tempDirWithPrefix = Files.createTempDirectory(prefix);
    final ObjectMapper objectMapper = new ObjectMapper();
    final Random random = new Random(100);
    String[] ids = new String[5];
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    for (int i = 0; i < 5; i++) {
      ids[i] = UUID.randomUUID().toString();
      objectMapper.writeValue(new File(String.format("%s/%s.json",tempDirWithPrefix.toString(),  ids[i])),
          ProducerInstrument.builder()
              .asOf(System.currentTimeMillis())
              .id(ids[i])
              .price(random.nextFloat())
              .build()
      );
    }
    final ConsumerConfig consumerConfig = new ConsumerConfig();
    consumerConfig.setTaskTimeOut(5);
    final InMemoryInstrumentPriceStorage storage = new InMemoryInstrumentPriceStorage();
final InstrumentDataProcessor instrumentDataProcessor = new InstrumentDataProcessor(new ObjectMapper(), storage);

    // WHEN
    instrumentDataProcessor.consumeDataTask(ConsumerMessage.builder()
        .totalFiles(5)
        .folderPath(tempDirWithPrefix.toString())
        .build());

    // THEN
    assertThat(storage.get(ids[0])).isNotNull();
    assertThat(storage.get(ids[1])).isNotNull();
    assertThat(storage.get(ids[2])).isNotNull();
    assertThat(storage.get(ids[3])).isNotNull();
    assertThat(storage.get(ids[4])).isNotNull();
  }

}