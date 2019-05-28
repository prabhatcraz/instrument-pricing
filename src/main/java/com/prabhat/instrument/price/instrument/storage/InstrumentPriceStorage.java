package com.prabhat.instrument.price.instrument.storage;


import java.util.List;

public interface InstrumentPriceStorage {
  Instrument get(final String id);

  void addPrice(Instrument instrumentPrice);

  List<Instrument> getAll();
}
