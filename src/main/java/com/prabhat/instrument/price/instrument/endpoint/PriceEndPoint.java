package com.prabhat.instrument.price.instrument.endpoint;

import com.prabhat.instrument.price.instrument.storage.Instrument;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PriceEndPoint {
    private final InstrumentPriceService instrumentPriceService;

    @GetMapping("/{instrumentId}/price")
    public Instrument getInstrument(@PathVariable(value = "instrumentId") final String instrumentId) {
        return instrumentPriceService.getInstrument(instrumentId);
    }

    /**
     * Only for demo purposes
     */
    @GetMapping("/instruments")
    public List<String> getAllInstruments() {
        return instrumentPriceService.getAll();
    }
}
