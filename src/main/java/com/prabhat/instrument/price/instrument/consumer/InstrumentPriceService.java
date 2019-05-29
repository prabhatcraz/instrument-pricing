package com.prabhat.instrument.price.instrument.consumer;

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

    /**
     * Only for demo purposes
     */
    public List<String> getAll() {
        return instrumentPriceStorage.getAll();
    }
}
