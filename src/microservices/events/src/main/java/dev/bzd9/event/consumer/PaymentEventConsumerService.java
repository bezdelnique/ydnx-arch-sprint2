package dev.bzd9.event.consumer;

import dev.bzd9.event.model.PaymentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
public class PaymentEventConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentEventConsumerService.class);

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "user-group")
    public void consume(PaymentEvent paymentEvent) {
        logger.info("Processing payment with ID: {} of user: {}"
                , paymentEvent.getPaymentId(), paymentEvent.getUserId());
    }

}
