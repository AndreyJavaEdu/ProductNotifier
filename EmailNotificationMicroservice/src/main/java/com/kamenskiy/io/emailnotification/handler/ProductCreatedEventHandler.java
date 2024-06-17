package com.kamenskiy.io.emailnotification.handler;

import com.kamenskiy.io.core.ProductCreatedEvent;
import com.kamenskiy.io.emailnotification.exception.NonRetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "product-created-events-topic")
public class ProductCreatedEventHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @KafkaHandler
    public void handle(ProductCreatedEvent productCreatedEvent){
        if (true)
            throw new NonRetryableException("Non retryable exception");
        LOGGER.info("Received product created event: {}", productCreatedEvent.getTitle());
    }
}
