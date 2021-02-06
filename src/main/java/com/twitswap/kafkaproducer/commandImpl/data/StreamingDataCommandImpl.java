package com.twitswap.kafkaproducer.commandImpl.data;

import com.twitswap.kafkaproducer.command.data.StreamingDataCommand;
import com.twitswap.kafkaproducer.entity.PopularHashtags;
import com.twitswap.kafkaproducer.model.response.StreamingDataResponse;
import com.twitswap.kafkaproducer.repository.PopularHashtagsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class StreamingDataCommandImpl implements StreamingDataCommand {
  @Autowired
  private PopularHashtagsRepository popularHashtagsRepository;


  @Override
  public Mono<StreamingDataResponse> execute(Boolean request) {
    StreamingDataResponse streamingDataResponse = new StreamingDataResponse();

    PopularHashtags popularHashtags = new PopularHashtags();
    Mono<PopularHashtags> popularHashtagsResponse = popularHashtagsRepository.findTopByOrderByCreatedAt();
    BeanUtils.copyProperties(popularHashtagsResponse, popularHashtags);
    streamingDataResponse.setPopularHashtags(popularHashtags);

    return Mono.just(streamingDataResponse);
  }
}
