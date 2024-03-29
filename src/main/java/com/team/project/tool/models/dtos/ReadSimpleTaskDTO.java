package com.team.project.tool.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReadSimpleTaskDTO {
    @NotNull(message = "Id must not be null.")
    private Long id;
    @NotNull(message = "Title must not be null.")
    private String title;
    private List<Long> userIds;
    @NotNull(message = "Status id must not be null.")
    private Long statusId;
}
