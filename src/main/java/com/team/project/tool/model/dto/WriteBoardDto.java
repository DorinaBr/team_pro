package com.team.project.tool.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WriteBoardDto {
    @NotNull(message = "Name must not be null.")
    @Size(min = 2, message = "Name must be at least 2 characters long.")
    private String name;
    @NotNull(message = "Owner id must not be null.")
    private Long ownerId;
}


