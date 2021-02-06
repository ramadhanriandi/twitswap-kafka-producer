package com.twitswap.kafkaproducer.command.data;

import com.blibli.oss.command.Command;
import com.twitswap.kafkaproducer.model.response.StreamingDataResponse;

public interface StreamingDataCommand extends Command<Boolean, StreamingDataResponse> {
}
