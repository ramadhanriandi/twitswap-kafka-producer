package com.twitswap.kafkaproducer.producer;

import com.google.common.collect.Lists;
import com.twitswap.kafkaproducer.config.KafkaConfig;
import com.twitswap.kafkaproducer.config.TwitterConfig;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TwitterProducer {
  final Logger logger = LoggerFactory.getLogger(TwitterProducer.class);

  private Client client;
  private KafkaProducer<String, String> producer;
  private BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(30);
  private List<String> trackTerms = Lists.newArrayList("indonesia", "2020");

  public Client createTwitterClient(BlockingQueue<String> msgQueue){
    Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);

    StatusesFilterEndpoint hbEndpoint = new StatusesFilterEndpoint();
    hbEndpoint.trackTerms(trackTerms);

    Authentication hosebirdAuth = new OAuth1(TwitterConfig.CONSUMER_KEYS, TwitterConfig.CONSUMER_SECRETS, TwitterConfig.TOKEN, TwitterConfig.SECRET);

    ClientBuilder builder = new ClientBuilder()
            .name("Hosebird-Client")
            .hosts(hosebirdHosts)
            .authentication(hosebirdAuth)
            .endpoint(hbEndpoint)
            .processor(new StringDelimitedProcessor(msgQueue));

    Client hbClient = builder.build();

    return hbClient;
  }

  private KafkaProducer<String, String> createKafkaProducer() {
    Properties prop = new Properties();
    prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAPSERVERS);
    prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

    prop.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
    prop.setProperty(ProducerConfig.ACKS_CONFIG, KafkaConfig.ACKS_CONFIG);
    prop.setProperty(ProducerConfig.RETRIES_CONFIG, KafkaConfig.RETRIES_CONFIG);
    prop.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, KafkaConfig.MAX_IN_FLIGHT_CONN);

    prop.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, KafkaConfig.COMPRESSION_TYPE);
    prop.setProperty(ProducerConfig.LINGER_MS_CONFIG, KafkaConfig.LINGER_CONFIG);
    prop.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, KafkaConfig.BATCH_SIZE);

    return new KafkaProducer<>(prop);
  }

  public void run(){
    logger.info("Setting up");

    client = createTwitterClient(msgQueue);
    client.connect();

    producer = createKafkaProducer();

    Runtime.getRuntime().addShutdownHook(new Thread( () -> {
      logger.info("Application is not stopping!");
      client.stop();
      logger.info("Closing Producer");
      producer.close();
      logger.info("Finished closing");
    }));

    while (!client.isDone()) {
      String msg = null;

      try {
        msg = msgQueue.poll(5, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        e.printStackTrace();
        client.stop();
      }

      if (msg != null) {
        logger.info(msg);
        producer.send(new ProducerRecord<>(KafkaConfig.TOPIC, null, msg), (recordMetadata, e) -> {
          if (e != null) {
            logger.error("Some error OR something bad happened", e);
          }
        });
      }
    }

    logger.info("\n Application End");
  }
}
