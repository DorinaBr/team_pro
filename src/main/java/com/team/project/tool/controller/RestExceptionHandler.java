package com.team.project.tool.controller;


import com.team.project.tool.exception.DuplicateStatusNameException;
import com.team.project.tool.exception.InvalidInputException;
import com.team.project.tool.model.dto.ErrorDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleEntityNotFoundException(EntityNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDto.builder().message("Resource not found.").build();
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleInvalidInputException(InvalidInputException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDto.builder().message(exception.getMessage()).build();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleRuntimeException(RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDto.builder().message("An internal server error has occurred.").build();
    }
}
