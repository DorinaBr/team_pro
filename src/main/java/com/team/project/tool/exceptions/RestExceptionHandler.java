package com.team.project.tool.exceptions;


import com.team.project.tool.models.dtos.ErrorDTO;
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
    public ErrorDTO handleEntityNotFoundException(EntityNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDTO.builder().message("Resource not found.").build();
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleInvalidInputException(InvalidInputException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDTO.builder().message(exception.getMessage()).build();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleRuntimeException(RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDTO.builder().message("An internal server error has occurred.").build();
    }
}
