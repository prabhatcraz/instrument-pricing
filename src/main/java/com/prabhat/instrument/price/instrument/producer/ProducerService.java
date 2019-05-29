package com.prabhat.instrument.price.instrument.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.prabhat.instrument.price.instrument.broker.ConsumerMessage;
import com.prabhat.instrument.price.instrument.broker.MessageBroker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.apache.naming.SelectorContext.prefix;

@Component
@AllArgsConstructor
@Slf4j
public class ProducerService {
  private final MessageBroker messageBroker;
  private final List<ProducerInstrument> producedInstruments = new ArrayList<>();

  /**
   * Produce a ramdom number of instruments and puts a message in the broker.
   */
  public void produce() throws IOException, InterruptedException {
    final Path tempDirWithPrefix = Files.createTempDirectory(prefix);
    log.info("New folder created: " + tempDirWithPrefix.toString());

    final ObjectMapper objectMapper = new ObjectMapper();
    final Random random = new Random();
    int numberOdInstrumentsToProduce = random.nextInt(100);

    // Put the message the moment we know how many files would be created.
    messageBroker.put(ConsumerMessage.builder()
        .folderPath(tempDirWithPrefix.toString())
        .totalFiles(numberOdInstrumentsToProduce)
        .build());


    final List<String> ids = new ArrayList<>(numberOdInstrumentsToProduce);

    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    for (int i = 0; i < numberOdInstrumentsToProduce; i++) {
      ids.add(i, UUID.randomUUID().toString());
      objectMapper.writeValue(new File(String.format("%s/%s.json", tempDirWithPrefix.toString(), ids.get(i))),
          ProducerInstrument.builder()
              .asOf(System.currentTimeMillis())
              .id(ids.get(i))
              .price(random.nextFloat())
              .build());
    }
  }
}
