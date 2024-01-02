package com.team.project.tool.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WriteStatusDTO {
    @NotNull(message = "Name must not be null.")
    private String name;
    private Integer position;
}
