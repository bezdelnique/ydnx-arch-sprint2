package dev.bzd9.event.producer;

import dev.bzd9.event.model.PaymentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class PaymentEventProducerService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentEventProducerService.class);

    @Value("${app.kafka.topic}")
    private String topicName;

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public PaymentEventProducerService(KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPayment(PaymentEvent paymentEvent) {
        logger.info("Sending payment: {}", paymentEvent);

        CompletableFuture<SendResult<String, PaymentEvent>> future =
                kafkaTemplate.send(topicName, paymentEvent.getPaymentId().toString(), paymentEvent);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Sent message=[{}] with offset=[{}]",
                        paymentEvent, result.getRecordMetadata().offset());
            } else {
                logger.error("Unable to send message=[{}] due to: {}",
                        paymentEvent, ex.getMessage());
            }
        });
    }
}
