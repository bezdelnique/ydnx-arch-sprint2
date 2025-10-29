package dev.bzd9.event.consumer;

import dev.bzd9.event.model.MovieEvent;
import dev.bzd9.event.model.PaymentEvent;
import dev.bzd9.event.model.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
public class MovieEventConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(MovieEventConsumerService.class);

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "user-group")
    public void consume(MovieEvent movieEvent) {
        logger.info("Processing movie with ID: {} for user: {}"
                , movieEvent.getMovieId(), movieEvent.getUserId());
    }

}
