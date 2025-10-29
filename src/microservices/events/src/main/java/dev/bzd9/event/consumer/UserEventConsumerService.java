package dev.bzd9.event.consumer;

import dev.bzd9.event.model.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
public class UserEventConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(UserEventConsumerService.class);

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "user-group")
    public void consume(UserEvent userEvent) {
        logger.info("Received User: {}", userEvent);
        logger.info("Processing user with ID: {} and Name: {}", userEvent.getUserId(), userEvent.getUsername());
    }

}
