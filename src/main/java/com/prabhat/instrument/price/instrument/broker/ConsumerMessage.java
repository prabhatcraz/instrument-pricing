package com.prabhat.instrument.price.instrument.broker;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ConsumerMessage {
  @NonNull
  String folderPath;
  @NonNull
  int totalFiles;
}
