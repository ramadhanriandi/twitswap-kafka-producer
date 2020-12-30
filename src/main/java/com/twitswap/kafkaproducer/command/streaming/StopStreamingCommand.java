package com.twitswap.kafkaproducer.command.streaming;

import com.blibli.oss.command.Command;
import com.twitswap.kafkaproducer.model.response.StartStreamingResponse;

public interface StopStreamingCommand extends Command<Boolean, StartStreamingResponse> {
}
