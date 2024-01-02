package com.team.project.tool.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WriteStatusDto {
    @NotNull(message = "Name must not be null.")
    private String name;
    private Integer position;
}
