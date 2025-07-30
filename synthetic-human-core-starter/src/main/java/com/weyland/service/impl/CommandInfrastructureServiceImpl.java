package com.weyland.service.impl;

import java.util.concurrent.*;

import com.weyland.domain.dto.Command;
import com.weyland.domain.enums.CommandPriority;
import com.weyland.service.contract.CommandExecutor;
import com.weyland.service.contract.CommandInfrastructureService;
import com.weyland.service.contract.MetricService;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CommandInfrastructureServiceImpl implements CommandInfrastructureService {

    private final CommandExecutor commandExecutor;

    private final MetricService metricService;

    private final MeterRegistry meterRegistry;

    private final BlockingQueue<Command> commandQueue = new LinkedBlockingQueue<>(1000); 

    private final ExecutorService executor = Executors.newFixedThreadPool(4); 

    @PostConstruct
    public void init() {
        executor.submit(this::processQueue); 
        metricService.registerMetrics(commandQueue);
    }

    @PreDestroy
    public void shutdown() {
        executor.shutdownNow(); 
    }

    private void processQueue() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Command command = commandQueue.take(); 
                commandExecutor.executeCommand(command); 
                meterRegistry.counter("android.commands.executed",
                        "author", command.getAuthor(),
                        "priority", command.getPriority().name())
                    .increment();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void submitCommand(Command command) {
        if (command.getPriority() == CommandPriority.CRITICAL) {
            commandExecutor.executeCommand(command); 
            meterRegistry.counter("android.commands.executed",
                        "author", command.getAuthor(),
                        "priority", command.getPriority().name())
                    .increment();
        } else {
            boolean added = commandQueue.offer(command); 
            if (!added) {
                throw new IllegalStateException("Command queue is full!");
            }
        }
    }

}