package com.prabhat.instrument.price.instrument.endpoint;

import com.prabhat.instrument.price.instrument.storage.Instrument;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class InstrumentEndPoint {
    private final InstrumentPriceService instrumentPriceService;

    /**
     * Return an instrument with its latest price
     */
    @GetMapping("instrument/{instrumentId}")
    public Instrument getInstrument(@PathVariable(value = "instrumentId") final String instrumentId) {
        return instrumentPriceService.getInstrument(instrumentId);
    }

    /**
     * Only for demo purposes, returns all the instrument ids stored in memory.
     */
    @GetMapping("/instruments")
    public List<String> getAllInstruments() {
        return instrumentPriceService.getAll();
    }
}
