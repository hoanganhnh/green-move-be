package com.rimmelasghar.boilerplate.springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> handleNotFoundException(NotFoundException exception) {
        ApiExceptionResponse response = new ApiExceptionResponse(
                exception.getErrorMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiExceptionResponse> handleConflictException(ConflictException exception) {
        ApiExceptionResponse response = new ApiExceptionResponse(
                exception.getErrorMessage(),
                HttpStatus.CONFLICT,
                LocalDateTime.now()
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
