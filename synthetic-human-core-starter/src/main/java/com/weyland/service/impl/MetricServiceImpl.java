package com.weyland.service.impl;

import java.util.concurrent.BlockingQueue;

import org.springframework.stereotype.Service;

import com.weyland.domain.dto.Command;
import com.weyland.service.contract.MetricService;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MetricServiceImpl implements MetricService {

    private final MeterRegistry meterRegistry;
    
    public void registerMetrics(BlockingQueue<Command> commandQueue) {
        Gauge.builder("android.queue.size", commandQueue::size)
            .description("Current tasks in queue")
            .register(meterRegistry);

        Counter.builder("android.commands.executed")
            .description("Total executed commands")
            .tag("priority", "COMMON")
            .register(meterRegistry);
    }

}
