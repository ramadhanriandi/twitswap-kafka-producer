package com.twitswap.kafkaproducer.consumer;

import com.twitswap.kafkaproducer.entity.TweetCount;
import com.twitswap.kafkaproducer.repository.TweetCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TweetCountsConsumer {
  @Autowired
  private TweetCountRepository tweetCountRepository;

  @KafkaListener(topics = "tweet-count-topic", groupId = "group_id")
  public void consume(String message) {
    if (message.length() > 0) {
      TweetCount tweetCount = new TweetCount();
      tweetCount.setData(Integer.parseInt(message));
      tweetCount.setCreatedAt(new Date());
      tweetCountRepository.save(tweetCount);
    }
  }
}
