package com.twitswap.kafkaproducer.command.data;

import com.blibli.oss.command.Command;
import com.twitswap.kafkaproducer.model.response.StreamDataResponse;

public interface StreamDataCommand extends Command<Boolean, StreamDataResponse> {
}
