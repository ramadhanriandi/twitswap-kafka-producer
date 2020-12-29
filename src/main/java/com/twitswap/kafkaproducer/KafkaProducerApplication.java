package com.twitswap.kafkaproducer;

import com.twitswap.kafkaproducer.producer.TwitterProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
//		new TwitterProducer().run();
	}
}
