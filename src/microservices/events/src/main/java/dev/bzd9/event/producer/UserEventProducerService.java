package dev.bzd9.event.producer;

import dev.bzd9.event.config.KafkaConfig;
import dev.bzd9.event.model.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserEventProducerService {

    private static final Logger logger = LoggerFactory.getLogger(UserEventProducerService.class);

    private final KafkaConfig kafkaConfig;
    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public UserEventProducerService(KafkaConfig kafkaConfig, KafkaTemplate<String, UserEvent> kafkaTemplate) {
        this.kafkaConfig = kafkaConfig;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUser(UserEvent userEvent) {
        logger.info("Sending user: {}", userEvent);

        CompletableFuture<SendResult<String, UserEvent>> future =
                kafkaTemplate.send(kafkaConfig.getTopicUserEvents(), userEvent.getUserId().toString(), userEvent);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Sent message=[{}] with offset=[{}]",
                        userEvent, result.getRecordMetadata().offset());
            } else {
                logger.error("Unable to send message=[{}] due to: {}",
                        userEvent, ex.getMessage());
            }
        });
    }
}
