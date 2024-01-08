package com.team.project.tool.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class BoardNotFoundException extends EntityNotFoundException {

    public BoardNotFoundException() {
        super("Board not found.");
    }
}
