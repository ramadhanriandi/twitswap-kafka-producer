package com.twitswap.kafkaproducer.model.response;

import com.twitswap.kafkaproducer.entity.PopularHashtags;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StreamDataResponse {
  private PopularHashtags popularHashtags;
}
