package dev.bzd9.event.controller;

import dev.bzd9.event.model.MovieEvent;
import dev.bzd9.event.model.PaymentEvent;
import dev.bzd9.event.model.UserEvent;
import dev.bzd9.event.producer.MovieEventProducerService;
import dev.bzd9.event.producer.PaymentEventProducerService;
import dev.bzd9.event.producer.UserEventProducerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
public class EventController {

    private final UserEventProducerService userProducer;
    private final MovieEventProducerService movieProducer;
    private final PaymentEventProducerService paymentProducer;

    @GetMapping("/api/events/health")
    public Map<String, Boolean> getHealthStatus() {
        return Map.of("status", true);
    }

    @RequestMapping("/api/events/user")
    public ResponseEntity<Map<String, String>> createUserEvent(@RequestBody UserEvent userEvent) {
        userProducer.sendUser(userEvent);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("status", "success"));
    }

    @RequestMapping("/api/events/movie")
    public ResponseEntity<Map<String, String>> createMovieEvent(@RequestBody MovieEvent movieEvent) {
        movieProducer.sendMovie(movieEvent);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("status", "success"));
    }

    @RequestMapping("/api/events/payment")
    public ResponseEntity<Map<String, String>> createPaymentEvent(@RequestBody PaymentEvent paymentEvent) {
        paymentProducer.sendPayment(paymentEvent);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("status", "success"));
    }

}
