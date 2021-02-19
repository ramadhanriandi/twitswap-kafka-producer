package com.twitswap.kafkaproducer.repository;

import com.twitswap.kafkaproducer.entity.TweetCount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TweetCountRepository extends MongoRepository<TweetCount, UUID> {
  TweetCount findTopByOrderByCreatedAtDesc();
}



