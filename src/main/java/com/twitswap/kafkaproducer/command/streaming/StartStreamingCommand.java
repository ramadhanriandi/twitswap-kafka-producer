package com.twitswap.kafkaproducer.command.streaming;

import com.blibli.oss.command.Command;
import com.twitswap.kafkaproducer.model.request.StartStreamingRequest;
import com.twitswap.kafkaproducer.model.response.StartStreamingResponse;

public interface StartStreamingCommand extends Command<StartStreamingRequest, StartStreamingResponse> {
}
