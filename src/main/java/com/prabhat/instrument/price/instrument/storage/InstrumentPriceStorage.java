package com.prabhat.instrument.price.instrument.storage;


public interface InstrumentPriceStorage {
  Instrument get(final String id);

  void addPrice(Instrument instrumentPrice);
}
