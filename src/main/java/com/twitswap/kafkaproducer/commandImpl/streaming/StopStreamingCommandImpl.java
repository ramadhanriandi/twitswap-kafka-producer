package com.twitswap.kafkaproducer.commandImpl.streaming;

import com.twitswap.kafkaproducer.command.streaming.StopStreamingCommand;
import com.twitswap.kafkaproducer.model.request.StartStreamingRequest;
import com.twitswap.kafkaproducer.model.response.StartStreamingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class StopStreamingCommandImpl implements StopStreamingCommand {
  @Override
  public Mono<StartStreamingResponse> execute(Boolean stop) {
    System.out.println("Stop Streaming Gan");

    return Mono.just(new StartStreamingResponse(true, "Stop streaming successfully"));
  }
}
