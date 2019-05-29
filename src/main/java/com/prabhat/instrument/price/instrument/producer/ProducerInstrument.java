package com.prabhat.instrument.price.instrument.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is the data format of producer. We are keeping two different models for consumer and producer even if they have
 * same fields, as in real life, the producer data format might(and will) vary.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducerInstrument {
    private String id;
    private float price;
    private long asOf;
}
