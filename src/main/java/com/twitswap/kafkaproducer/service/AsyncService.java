package com.twitswap.kafkaproducer.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AsyncService {
  private ExecutorService executorService;

  @PostConstruct
  private void create() {
    executorService = Executors.newSingleThreadExecutor();
  }

  public void process(Runnable operation) {
    executorService.submit(operation);
  }


  @PreDestroy
  private void destroy() {
    executorService.shutdown();
  }
}