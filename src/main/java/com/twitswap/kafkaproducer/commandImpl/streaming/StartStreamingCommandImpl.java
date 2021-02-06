package com.twitswap.kafkaproducer.commandImpl.streaming;

import com.twitswap.kafkaproducer.command.streaming.StartStreamingCommand;
import com.twitswap.kafkaproducer.model.request.StartStreamingRequest;
import com.twitswap.kafkaproducer.model.response.StartStreamingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class StartStreamingCommandImpl implements StartStreamingCommand {
  @Override
  public Mono<StartStreamingResponse> execute(StartStreamingRequest request) {

    return Mono.just(new StartStreamingResponse(true, "Start streaming successfully"));
  }
}
