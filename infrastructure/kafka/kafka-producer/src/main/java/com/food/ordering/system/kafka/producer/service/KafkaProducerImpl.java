package com.food.ordering.system.kafka.producer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import com.food.ordering.system.kafka.producer.exception.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PreDestroy;
import java.io.Serializable;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K, V> {

    private final KafkaTemplate<K, V> kafkaTemplate;

    @Override
    public void send(String topicName, K key, V message, ListenableFutureCallback<SendResult<K, V>> callback) {
        log.info("Sending message {} to topic {}...", message, topicName);
        try {
            ListenableFuture<SendResult<K, V>> kafkaResultFuture = kafkaTemplate.send(topicName, key, message);
            kafkaResultFuture.addCallback(callback);
        } catch (KafkaProducerException e) {
            log.error(e.getMessage());
            throw new KafkaProducerException("Error on kafka producer " + key + ", message " + message);
        }
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("closing kafka producer...");
            kafkaTemplate.destroy();
        }
    }
}
