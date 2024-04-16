package com.hefto.heritage.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hefto.heritage.dto.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.SendResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


import java.util.concurrent.CompletableFuture;

@Service
public class PersonProducerImpl implements PersonProducer {

    private static final Logger logger = LoggerFactory.getLogger(PersonProducerImpl.class);
    private static final String TOPIC = "heritage_birth";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Long key, Person person) {


        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String personJSON = ow.writeValueAsString(person);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC, key.toString(), personJSON);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + key +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        key + "] due to : " + ex.getMessage());
            }
        });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}