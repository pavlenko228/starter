package com.weyland.domain.dto;

import com.weyland.domain.enums.CommandPriority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Command {
    @NotBlank @Size(max = 1000)
    private String description;

    @NotNull
    private CommandPriority priority; 

    @NotBlank @Size(max = 100)
    private String author;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z$")
    private String time;
}
