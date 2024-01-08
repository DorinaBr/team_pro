package com.team.project.tool.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class StatusNotFoundException extends EntityNotFoundException {

    public StatusNotFoundException() {
        super("Status not found.");
    }
}
