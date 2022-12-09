package com.food.ordering.system.order.service.messaging.publisher.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class OrderKafkaMessageHelper {
    public <T> ListenableFutureCallback<SendResult<String, T>>
    getKafkaCallback(String responseTopicName, T requestAvroModel, String orderId, String responseAvroModelName) {
        return new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, T> result) {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                log.info("response received: order={}, topic={}, partition={}, offset={}, timestamp={}",
                        orderId,
                        recordMetadata.topic(),
                        recordMetadata.partition(),
                        recordMetadata.offset(),
                        recordMetadata.timestamp());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("failed to send {} message {} to topic {}",
                        responseAvroModelName, requestAvroModel.toString(), responseTopicName, ex);
            }
        };
    }
}
