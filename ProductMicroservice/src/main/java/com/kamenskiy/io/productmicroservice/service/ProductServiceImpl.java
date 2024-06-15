package com.kamenskiy.io.productmicroservice.service;

import com.kamenskiy.io.productmicroservice.service.dto.CreateProductDto;
import com.kamenskiy.io.productmicroservice.service.event.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class ProductServiceImpl implements ProductService {
   private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductDto productDto) throws ExecutionException, InterruptedException {
        //TODO save to DB
        String productId = UUID.randomUUID().toString();

        ProductCreatedEvent event = new ProductCreatedEvent(productId, productDto.getTitle(), productDto.getPrice(),
                productDto.getQuantity());
        SendResult<String, ProductCreatedEvent> result = kafkaTemplate
                .send("product-created-events-topic", productId, event).get();
        LOGGER.info(String.valueOf(kafkaTemplate
                .send("product-created-events-topic", productId, event)));
        LOGGER.info("topic: {}", result.getRecordMetadata().topic());
        LOGGER.info("partition: {}", result.getRecordMetadata().partition());
        LOGGER.info("Offset: {}", result.getRecordMetadata().offset());

        LOGGER.info("Return productId: {}", productId);
        return productId;
    }
}
