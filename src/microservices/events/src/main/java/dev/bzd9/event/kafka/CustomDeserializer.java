package dev.bzd9.event.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.bzd9.event.model.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

@Slf4j
public class CustomDeserializer implements Deserializer<UserEvent> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public UserEvent deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), UserEvent.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to UserEvent");
        }
    }

    @Override
    public void close() {
    }

}
