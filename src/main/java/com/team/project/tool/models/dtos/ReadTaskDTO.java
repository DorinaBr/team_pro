package com.team.project.tool.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ReadTaskDTO {
    @NotNull(message = "Id must not be null.")
    private Long id;
    @NotNull(message = "Title must not be null.")
    private String title;
    @NotNull(message = "Description must not be null.")
    private String description;
    private List<Long> userIds;
    @NotNull(message = "Status id must not be null.")
    private Long statusId;
    @NotNull(message = "Task creator id must not be null.")
    private Long createdById;
    @NotNull(message = " Date and time must not be null.")
    private LocalDateTime createdAt;


}
