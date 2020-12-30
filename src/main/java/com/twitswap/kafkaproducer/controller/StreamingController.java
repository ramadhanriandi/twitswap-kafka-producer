package com.twitswap.kafkaproducer.controller;

import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import com.twitswap.kafkaproducer.command.streaming.StartStreamingCommand;
import com.twitswap.kafkaproducer.command.streaming.StopStreamingCommand;
import com.twitswap.kafkaproducer.model.request.StartStreamingRequest;
import com.twitswap.kafkaproducer.model.response.StartStreamingResponse;
import com.twitswap.kafkaproducer.producer.TwitterProducer;
import com.twitswap.kafkaproducer.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = StreamingControllerPath.BASE_PATH)
public class StreamingController {
  private AsyncService asyncService;
  private CommandExecutor commandExecutor;
  private TwitterProducer twitterProducer;

  @Autowired
  public StreamingController(CommandExecutor commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  @Autowired
  public void setAsyncService(AsyncService asyncService) {
    this.asyncService = asyncService;
  }

  public void setTwitterProducer(TwitterProducer twitterProducer) {
    this.twitterProducer = twitterProducer;
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Response<StartStreamingResponse>> startStreaming(@RequestParam(required = false) boolean stop, @RequestBody(required = false) StartStreamingRequest request) {
    if (stop) {
      asyncService.stop();

      return commandExecutor.execute(StopStreamingCommand.class, true)
              .log("#startStreaming - stop - Successfully executing the command")
              .map(ResponseHelper::ok)
              .subscribeOn(Schedulers.elastic());
    }

    setTwitterProducer(new TwitterProducer(request.getTags()));
    asyncService.process(() -> this.twitterProducer.run());

    return commandExecutor.execute(StartStreamingCommand.class, request)
            .log("#startStreaming - start - Successfully executing the command")
            .map(ResponseHelper::ok)
            .subscribeOn(Schedulers.elastic());
  }
}
