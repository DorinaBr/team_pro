package com.team.project.tool.exception;

public class DuplicateStatusNameException extends InvalidInputException {
    public DuplicateStatusNameException(Throwable cause) {
        super("A status with the same name already exists on this board.", cause);
    }
}
