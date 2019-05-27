package com.prabhat.instrument.price.instrument.consumer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "consumer")
public class ConsumerConfig {
    private int taskTimeOut;
}
