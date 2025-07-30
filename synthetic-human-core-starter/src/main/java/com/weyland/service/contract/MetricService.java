package com.weyland.service.contract;

import java.util.concurrent.BlockingQueue;

import com.weyland.domain.dto.Command;

public interface MetricService {
    void registerMetrics(BlockingQueue<Command> commandQueue);
}
