package com.prabhat.instrument.price.instrument.storage;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Instrument {
  @NonNull
  String id;
  @NonNull
  float price;
  @NonNull
  long asOf;
}
