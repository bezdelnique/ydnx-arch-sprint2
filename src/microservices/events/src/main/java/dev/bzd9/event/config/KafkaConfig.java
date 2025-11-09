package dev.bzd9.event.config;

import lombok.Data;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@ConfigurationProperties(prefix = "app.kafka")
@Data
public class KafkaConfig {

    private String topicUserEvents;
    private String topicMovieEvents;
    private String topicPaymentEvents;

    @Bean
    public NewTopic userTopic() {
        return TopicBuilder.name(topicUserEvents)
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic movieTopic() {
        return TopicBuilder.name(topicMovieEvents)
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic paymentTopic() {
        return TopicBuilder.name(topicPaymentEvents)
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
