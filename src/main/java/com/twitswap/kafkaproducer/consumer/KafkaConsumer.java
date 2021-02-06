package com.twitswap.kafkaproducer.consumer;

import com.twitswap.kafkaproducer.entity.PopularHashtagData;
import com.twitswap.kafkaproducer.entity.PopularHashtags;
import com.twitswap.kafkaproducer.repository.PopularHashtagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class KafkaConsumer {
  @Autowired
  private PopularHashtagsRepository popularHashtagsRepository;

  @KafkaListener(topics = "popular-hashtag-topic", groupId = "group_id")
  public void consume(String message) {
    if (message.length() > 0) {
      String[] parsedMessage = message.split("\n");
      List<PopularHashtagData> popularHashtagDataList = new ArrayList<>();

      for (String msg : parsedMessage) {
        String[] parsedMsg = msg.split("\\|");
        popularHashtagDataList.add(new PopularHashtagData(Integer.parseInt(parsedMsg[1]), parsedMsg[0]));
      }

      PopularHashtags popularHashtags = new PopularHashtags();
      popularHashtags.setData(popularHashtagDataList);
      popularHashtags.setCreatedAt(new Date());
      popularHashtagsRepository.save(popularHashtags);
    }
  }
}
