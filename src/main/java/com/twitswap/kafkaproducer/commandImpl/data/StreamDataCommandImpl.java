package com.twitswap.kafkaproducer.commandImpl.data;

import com.twitswap.kafkaproducer.command.data.StreamDataCommand;
import com.twitswap.kafkaproducer.entity.PopularHashtags;
import com.twitswap.kafkaproducer.model.response.StreamDataResponse;
import com.twitswap.kafkaproducer.repository.PopularHashtagsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class StreamDataCommandImpl implements StreamDataCommand {
  @Autowired
  private PopularHashtagsRepository popularHashtagsRepository;

  @Override
  public Mono<StreamDataResponse> execute(Boolean request) {
    StreamDataResponse streamDataResponse = new StreamDataResponse();

    PopularHashtags popularHashtagsResponse = popularHashtagsRepository.findTopByOrderByCreatedAtDesc();
    streamDataResponse.setPopularHashtags(popularHashtagsResponse);

    return Mono.just(streamDataResponse);
  }
}
