package com.kamenskiy.io.productmicroservice.service;

import com.kamenskiy.io.core.ProductCreatedEvent;
import com.kamenskiy.io.productmicroservice.service.dto.CreateProductDto;

import org.apache.kafka.clients.producer.ProducerRecord;
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

        ProducerRecord<String, ProductCreatedEvent> record = new ProducerRecord<>("product-created-events-topic",
                productId, event);
        record.headers().add("messageId", "UUID.randomUUID().toString()".getBytes());

        SendResult<String, ProductCreatedEvent> result = kafkaTemplate
                .send(record).get();
        LOGGER.info("topic: {}", result.getRecordMetadata().topic());
        LOGGER.info("partition: {}", result.getRecordMetadata().partition());
        LOGGER.info("Offset: {}", result.getRecordMetadata().offset());

        LOGGER.info("Return productId: {}", productId);
        return productId;
    }
}
