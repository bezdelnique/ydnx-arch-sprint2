package dev.bzd9.event.consumer;

import dev.bzd9.event.model.MovieEvent;
import dev.bzd9.event.model.PaymentEvent;
import dev.bzd9.event.model.UserEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumerService.class);

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "event-group")
    public void consume(Object event, @Header(name = "__TypeId__", required = false) String type) {
        switch (((ConsumerRecord<?, ?>) event).value()) {
            case MovieEvent movieEvent -> processMovieEvent(movieEvent);
            case PaymentEvent paymentEvent -> processPaymentEvent(paymentEvent);
            case UserEvent userEvent -> processUserEvent(userEvent);
            case null, default -> logger.warn("Unknown event type: {}", event.getClass().getSimpleName());
        }
    }

    private void processMovieEvent(MovieEvent movieEvent) {
        logger.info("Processing movie with ID: {} for user: {}",
                movieEvent.getMovieId(), movieEvent.getUserId());
    }

    private void processPaymentEvent(PaymentEvent paymentEvent) {
        logger.info("Processing payment with ID: {} amount: {}",
                paymentEvent.getPaymentId(), paymentEvent.getAmount());
    }

    private void processUserEvent(UserEvent userEvent) {
        logger.info("Processing user with ID: {} action: {}",
                userEvent.getUserId(), userEvent.getAction());
    }

}

