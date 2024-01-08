package com.team.project.tool.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException() {
        super("User not found.");
    }
}
