package com.prabhat.instrument.price.instrument.config;

import com.prabhat.instrument.price.instrument.storage.InMemoryInstrumentPriceStorage;
import com.prabhat.instrument.price.instrument.storage.InstrumentPriceStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstrumentConfig {

  @Bean
  InstrumentPriceStorage getInstrumentPriceStorage() {
    return new InMemoryInstrumentPriceStorage();
  }
}
