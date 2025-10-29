package dev.bzd9.event.producer;

import dev.bzd9.event.model.MovieEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class MovieEventProducerService {

    private static final Logger logger = LoggerFactory.getLogger(MovieEventProducerService.class);

    @Value("${app.kafka.topic}")
    private String topicName;

    private final KafkaTemplate<String, MovieEvent> kafkaTemplate;

    public MovieEventProducerService(KafkaTemplate<String, MovieEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMovie(MovieEvent movieEvent) {
        logger.info("Sending movie: {}", movieEvent);

        CompletableFuture<SendResult<String, MovieEvent>> future =
                kafkaTemplate.send(topicName, movieEvent.getMovieId().toString(), movieEvent);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Sent message=[{}] with offset=[{}]",
                        movieEvent, result.getRecordMetadata().offset());
            } else {
                logger.error("Unable to send message=[{}] due to: {}",
                        movieEvent, ex.getMessage());
            }
        });
    }

}
