package com.twitswap.kafkaproducer.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class AsyncService {
  private ExecutorService executorService;
  private Future<?> future;

  @PostConstruct
  private void create() {
    executorService = Executors.newSingleThreadExecutor();
  }

  public void process(Runnable operation) {
    this.future = executorService.submit(operation);
  }

  public void stop() {
    this.future.cancel(true);
  }

  @PreDestroy
  private void destroy() {
    executorService.shutdown();
  }
}