package com.prabhat.instrument.price.instrument.storage;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryInstrumentPriceStorageTest {
    @Test
    public void testStreamConversion() {
        Instrument[] instruments = new Instrument[1000000];
        Random random = new Random();
        for(int i=0; i<1000000; i++) {
            instruments[i] = Instrument.builder()
                    .price(random.nextFloat())
                    .asOf(System.currentTimeMillis())
                    .id(UUID.randomUUID().toString())
                    .build();
        }
        List<Instrument> instrumentList = Arrays.asList(instruments);

        long now = System.currentTimeMillis();
        List<String> newInstruments = new ArrayList<>();
        for(Instrument instrument: instrumentList) {
            newInstruments.add(instrument.toString());
        }
        long then1 = System.currentTimeMillis();

        newInstruments = instrumentList.stream().map(Instrument::toString).collect(Collectors.toList());
        long then2 = System.currentTimeMillis();

    }
}