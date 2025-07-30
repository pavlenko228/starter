package com.weyland.service.contract;

import com.weyland.domain.dto.Command;

public interface CommandInfrastructureService {
    void init();
    void shutdown();
    void submitCommand(Command command);
}
