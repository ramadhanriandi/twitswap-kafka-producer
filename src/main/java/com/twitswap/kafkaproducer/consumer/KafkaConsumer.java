package com.twitswap.kafkaproducer.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
  @KafkaListener(topics = "popular-hashtag-topic", groupId = "group_id")
  public void consume(String message) {
    System.out.println(message);
  }
}
