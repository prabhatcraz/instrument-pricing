package com.prabhat.instrument.price.instrument.endpoint;

import com.prabhat.instrument.price.instrument.storage.Instrument;
import com.prabhat.instrument.price.instrument.storage.InstrumentPriceStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class InstrumentPriceService {
    private final InstrumentPriceStorage instrumentPriceStorage;

    public Instrument getInstrument(final String id) {
        return instrumentPriceStorage.get(id);
    }

    public List<Instrument> getAll() {
        return instrumentPriceStorage.getAll();
    }
}
