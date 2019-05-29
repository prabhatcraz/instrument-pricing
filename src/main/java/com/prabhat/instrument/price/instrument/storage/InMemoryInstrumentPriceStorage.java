package com.prabhat.instrument.price.instrument.storage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Stores prices of @{@link Instrument}s in a @{@link ConcurrentHashMap} of @{@link PriorityQueue}. The priority is
 * decided by the timestamp of the price provided by the producer.
 */
public class InMemoryInstrumentPriceStorage implements InstrumentPriceStorage {
  // We need the storage of prices per instrument concurrent and hence usage of a @PriorityBlockingQueue
  // whereas the map of instruments and prices could be a normal @HashMap.
  // If we used ConcurrentHashMap and a @PriorityQueue, its still not thread safe since, actual concurrency problem
  // is update of price of same instrument by multiple threads.
  private final Map<String, PriorityBlockingQueue<Instrument>> instrumentMap = new HashMap<>();

  public Instrument get(final String id) {
    final PriorityBlockingQueue<Instrument> instrumentPrices = instrumentMap.get(id);
    if (instrumentPrices == null) return null;
    return instrumentPrices.peek();
  }

  /**
   * Stores the price in the @{@link PriorityBlockingQueue} for each instrumentId.
   *
   * In real world, I will split the storage of instrument price in two parts to serve following use-cases.
   * 1. Latest price. => would be stored in one table. If we use DynamoDB, we can also conditional update to update the price only if
   * the price is more recent than existing price for the entry.
   * 2. Historical prices => would be stored in another table where InstrumentID is Primary key and asOf would be the sort key.
   *
   * A @{@link PriorityBlockingQueue} will make sure that the latest price is on top. We are using the comparator for comparing the
   * time of price in the priority queue.
   */
  public void addPrice(final Instrument instrument) {
    final PriorityBlockingQueue<Instrument> instrumentPrices = instrumentMap
        .computeIfAbsent(instrument.getId(),
                //  Since we are taking storage as for demo purpose, I am using initial capacity of 5.
                //  But its not practical for the usecase of storing 1 million records.
                // 10000000*4kb = 3.9GB , so 5 insertions would mean we need at ~20GB of ram. Possible but we would need
                // machines high ram.
                i -> new PriorityBlockingQueue<>(5, Comparator.comparingLong(Instrument::getAsOf)));

    instrumentPrices.add(instrument);
  }

  /**
   * Lists all instruments.
   */
  public List<String> getAll() {
    return new ArrayList<>(instrumentMap.keySet());
  }
}
