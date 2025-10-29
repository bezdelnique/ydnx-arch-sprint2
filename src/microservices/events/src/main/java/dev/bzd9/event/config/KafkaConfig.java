package dev.bzd9.event.config;

import dev.bzd9.event.model.MovieEvent;
import dev.bzd9.event.model.PaymentEvent;
import dev.bzd9.event.model.UserEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;

public class KafkaConfig {

    @Value("${app.kafka.topic}")
    private String topicName;

    @Bean
    public NewTopic userTopic() {
        return TopicBuilder.name(topicName)
                .partitions(3)
                .replicas(1)
                .build();
    }


//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, MovieEvent> movieEventContainerFactory(
//            ConsumerFactory<String, Object> consumerFactory) {
//        ConcurrentKafkaListenerContainerFactory<String, MovieEvent> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory);
//        factory.setRecordFilterStrategy(record ->
//                !(record.value() instanceof MovieEvent));
//        return factory;
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, PaymentEvent> paymentEventContainerFactory(
//            ConsumerFactory<String, Object> consumerFactory) {
//        ConcurrentKafkaListenerContainerFactory<String, PaymentEvent> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory);
//        factory.setRecordFilterStrategy(record ->
//                !(record.value() instanceof PaymentEvent));
//        return factory;
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, UserEvent> userEventContainerFactory(
//            ConsumerFactory<String, Object> consumerFactory) {
//        ConcurrentKafkaListenerContainerFactory<String, UserEvent> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory);
//        factory.setRecordFilterStrategy(record ->
//                !(record.value() instanceof UserEvent));
//        return factory;
//    }

}
