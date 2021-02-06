package com.twitswap.kafkaproducer.repository;

import com.twitswap.kafkaproducer.entity.PopularHashtags;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface PopularHashtagsRepository extends ReactiveMongoRepository<PopularHashtags, UUID> {
  Mono<PopularHashtags> findTopByOrderByCreatedAt();
}



