package com.prabhat.instrument.price.instrument.consumer;

import com.prabhat.instrument.price.instrument.broker.ConsumerMessage;
import com.prabhat.instrument.price.instrument.broker.MessageBroker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class MessageBrokerManager {
  private final MessageBroker messageBroker;
  private final Consumer consumer;

  @Scheduled(fixedDelay = 50)
  public void checkMessage() {
    try {
      final ConsumerMessage message = messageBroker.get();
      if(message != null) {
        log.info("message received, consuming now.");
        consumer.consumeWithTimeout(message);
      }
    } catch (Exception e) {
      log.error("Problem retrieving/consuming message {}", e.getMessage());
    }
  }
}
