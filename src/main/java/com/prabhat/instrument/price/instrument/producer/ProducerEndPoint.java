package com.prabhat.instrument.price.instrument.producer;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class ProducerEndPoint {
    private final ProducerService producerService;
    /**
     * Produces a bunch of random instruments and puts a message in the queue for consumer to consume.
     */
    @PostMapping("/produce")
    public void startProducing() throws IOException, InterruptedException {
        producerService.produce();
    }
}
