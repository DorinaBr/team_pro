package com.team.project.tool.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReadBoardDto {
    @NotNull(message = "Id must not be null.")
    private Long id;
    @NotNull(message = "Name must not be null.")
    @Size(min = 2, message = "Name must be at least 2 characters long.")
    private String name;
    @NotNull(message = "Name must not be null.")
    @Size(min = 2, message = "Name must be at least 2 characters long.")
    private ReadUserDto owner;
    @Min(value = 1, message = "The users list size must be equal or greater than 1.")
    @NotNull(message = "The users list size must not be null.")
    private List<ReadUserDto> users;
    @Min(value = 1, message = "The status list size must be equal or greater than 1.")
    @NotNull(message = "The status list size must not be null.")
    private List<ReadStatusDto> statuses;
    @Min(value = 1, message = "The task list size must be equal or greater than 1.")
    @NotNull(message = "The task list size must not be null.")
    private List<ReadTaskDto> tasks;
}
