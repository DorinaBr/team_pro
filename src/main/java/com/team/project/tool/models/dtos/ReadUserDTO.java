package com.team.project.tool.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadUserDTO {
    @NotNull(message = "Id must not be null.")
    private Long id;
    @NotNull(message = "First name must not be null.")
    @Size(min = 2, message = "First name must be at least 2 characters long.")
    private String firstName;
    @NotNull(message = "Last name must not be null.")
    @Size(min = 2, message = "Last name must be at least 2 characters long.")
    private String lastName;
    @NotNull(message = "Email address must not be null.")
    @Email
    private String email;
}
