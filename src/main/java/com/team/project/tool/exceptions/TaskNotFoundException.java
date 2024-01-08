package com.team.project.tool.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class TaskNotFoundException extends EntityNotFoundException {

    public TaskNotFoundException() {
        super("Task not found.");
    }
}
