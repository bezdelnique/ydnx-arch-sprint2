package dev.bzd9.event.controller;

import dev.bzd9.event.model.MovieEvent;
import dev.bzd9.event.model.PaymentEvent;
import dev.bzd9.event.model.UserEvent;
import dev.bzd9.event.producer.MovieEventProducerService;
import dev.bzd9.event.producer.PaymentEventProducerService;
import dev.bzd9.event.producer.UserEventProducerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class EventController {

    private final UserEventProducerService userProducer;
    private final MovieEventProducerService movieProducer;
    private final PaymentEventProducerService paymentProducer;

    @RequestMapping("/api/events/user")
    public String createUserEvent(@RequestBody UserEvent userEvent) {
        userProducer.sendUser(userEvent);
        return "Sent to Kafka successfully: %s".formatted(userEvent);
    }

    @RequestMapping("/api/events/movie")
    public String createMovieEvent(@RequestBody MovieEvent movieEvent) {
        movieProducer.sendMovie(movieEvent);
        return "Sent to Kafka successfully: %s".formatted(movieEvent);
    }

    @RequestMapping("/api/events/payment")
    public String createPaymentEvent(@RequestBody PaymentEvent paymentEvent) {
        paymentProducer.sendPayment(paymentEvent);
        return "Sent to Kafka successfully: %s".formatted(paymentEvent);
    }

}
