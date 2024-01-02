package com.team.project.tool.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WriteTaskDTO {
    @NotNull(message = "Description must not be null.")
    private String description;
    @NotNull(message = "Title must not be null.")
    private String title;
    private List<Long> userIds;
    @NotNull(message = "Status id must not be null.")
    private Long statusId;
    @NotNull(message = "The Id of the user that created the task must not be null.")
    private Long createdById;
}
