package com.weyland.service.impl;


import com.weyland.domain.dto.Command;
import com.weyland.service.contract.CommandExecutor;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CommandExecutorImpl implements CommandExecutor {

    public void executeCommand(Command command) {
        System.out.println("Executing: " + command.getDescription());
    }
 
}
