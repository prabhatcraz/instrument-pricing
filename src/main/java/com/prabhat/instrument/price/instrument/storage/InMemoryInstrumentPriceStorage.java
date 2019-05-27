package com.prabhat.instrument.price.instrument.storage;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

@Component
public class InMemoryInstrumentPriceStorage implements InstrumentPriceStorage {
  private final Map<String, PriorityQueue<Instrument>> instrumentMap = new HashMap<>();

  public Instrument get(final String id) {
    final PriorityQueue<Instrument> instrumentPrices = instrumentMap.get(id);
    if (instrumentPrices == null) return null;
    return instrumentPrices.peek();
  }

  /**
   * Stores the price in the @{@link PriorityQueue} for each instrumentId.
   *
   * In real world, I will split the storage of instrument price in two parts to serve following use-cases.
   * 1. Latest price. => would be stored in one table. If we use DynamoDB, we can also conditional update to update the price only if
   * the price is more recent than existing price for the entry.
   * 2. Historical prices => would be stored in another table where InstrumentID is Primary key and asOf would be the sort key.
   *
   * A @{@link PriorityQueue} will make sure that the latest price is on top. We are using the comparator for comparing the
   * time of price in the priority queue.
   */
  public void addPrice(final Instrument instrument) {
    final PriorityQueue<Instrument> instrumentPrices = instrumentMap.computeIfAbsent(instrument.getId(),
            i -> new PriorityQueue<>(Comparator.comparingLong(Instrument::getAsOf)));
    instrumentPrices.add(instrument);
  }
}
