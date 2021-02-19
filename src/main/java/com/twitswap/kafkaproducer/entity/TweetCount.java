package com.twitswap.kafkaproducer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = TweetCount.COLLECTION_NAME)
public class TweetCount {
  public static final String COLLECTION_NAME = "tweet_counts";

  public static final String FIELD_DATA = "data";
  public static final String FIELD_CREATED_AT = "createdAt";

  @Field(value = TweetCount.FIELD_DATA)
  private Integer data;

  @CreatedDate
  @Field(value = TweetCount.FIELD_CREATED_AT)
  private Date createdAt;
}
