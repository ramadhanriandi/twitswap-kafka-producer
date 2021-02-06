package com.twitswap.kafkaproducer.controller;

import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import com.twitswap.kafkaproducer.command.data.StreamDataCommand;
import com.twitswap.kafkaproducer.model.response.StreamDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = DataControllerPath.BASE_PATH)
public class DataController {
  @Autowired
  private CommandExecutor commandExecutor;

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Response<StreamDataResponse>> streamingData() {
    return commandExecutor.execute(StreamDataCommand.class, true)
            .log("#streamingData - Successfully executing the command")
            .map(ResponseHelper::ok)
            .subscribeOn(Schedulers.elastic());
  }
}
