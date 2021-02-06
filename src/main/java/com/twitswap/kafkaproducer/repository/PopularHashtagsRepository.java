package com.twitswap.kafkaproducer.repository;

import com.twitswap.kafkaproducer.entity.PopularHashtags;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PopularHashtagsRepository extends MongoRepository<PopularHashtags, UUID> {
  PopularHashtags findTopByOrderByCreatedAtDesc();
}



