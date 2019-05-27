package com.prabhat.instrument.price.instrument.consumer;

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
