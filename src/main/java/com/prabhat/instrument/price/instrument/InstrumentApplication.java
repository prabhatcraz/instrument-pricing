package com.prabhat.instrument.price.instrument;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InstrumentApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstrumentApplication.class, args);
	}

}
