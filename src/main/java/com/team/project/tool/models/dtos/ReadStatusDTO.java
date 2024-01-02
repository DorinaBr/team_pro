package com.team.project.tool.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadStatusDTO {
    @NotNull(message = "Id must not be null.")
    private Long id;
    @NotNull(message = "Name must not be null.")
    private String name;
    @NotNull(message = "Position must not be null.")
    private Integer position;
}
