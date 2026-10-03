package com.app.todo_hexagonal.infrastructure.adapter.in.rest;

import com.app.todo_hexagonal.domain.exception.InvalidTaskStateException;
import com.app.todo_hexagonal.domain.exception.TaskNotFoundException;
import com.app.todo_hexagonal.domain.exception.TaskValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ProblemDetail HandleTaskNotFound(TaskNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(404),
                ex.getMessage()
        );
        problemDetail.setTitle("Tarea no encontrada");
        return problemDetail;
    }

    @ExceptionHandler(InvalidTaskStateException.class)
    public ProblemDetail HandleInvalidTaskState(InvalidTaskStateException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(400),
                ex.getMessage()
        );
        problemDetail.setTitle("Estado no válido");
        return problemDetail;
    }

    @ExceptionHandler(TaskValidationException.class)
    public ProblemDetail HandleTaskValidation(TaskValidationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(400),
                ex.getMessage()
        );
        problemDetail.setTitle("Datos no válidos");
        return problemDetail;
    }

    // Errores de validación (DTO)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail HandleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Error de validación"
        );
        problemDetail.setTitle("Datos no válidos");
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

}
