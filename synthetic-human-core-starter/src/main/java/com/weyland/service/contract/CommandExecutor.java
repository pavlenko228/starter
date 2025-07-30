package com.weyland.service.contract;

import com.weyland.domain.dto.Command;

public interface CommandExecutor {
    void executeCommand(Command command);
}
