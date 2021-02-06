package com.twitswap.kafkaproducer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = PopularHashtags.COLLECTION_NAME)
public class PopularHashtags {
  public static final String COLLECTION_NAME = "popular_hashtags";

  public static final String FIELD_DATA = "data";
  public static final String FIELD_CREATED_AT = "createdAt";

  @Field(value = PopularHashtags.FIELD_DATA)
  private List<PopularHashtagData> data;

  @CreatedDate
  @Field(value = PopularHashtags.FIELD_CREATED_AT)
  private Date createdAt;
}
