package com.khanh.job.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse<Set<String>> handleBodyValidation(MethodArgumentNotValidException ex) {
    Set<String> errors = new LinkedHashSet<>();
    for (ObjectError error : ex.getBindingResult().getAllErrors()) {
      errors.add(Objects.requireNonNullElse(error.getDefaultMessage(), "Invalid value"));
    }
    return ErrorResponse.error(errors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse<Set<String>> handleConstraintViolation(ConstraintViolationException ex) {
    Set<String> errors = new LinkedHashSet<>();
    for (ConstraintViolation<?> v : ex.getConstraintViolations()) {
      errors.add(v.getPropertyPath() + ": " + v.getMessage());
    }
    return ErrorResponse.error(errors);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse<String> handleMissingParam(MissingServletRequestParameterException ex) {
    return ErrorResponse.error(ex.getMessage());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse<String> handleUnreadable(HttpMessageNotReadableException ex) {
    return ErrorResponse.error("Malformed request body");
  }

  @ExceptionHandler(AuthenticationException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorResponse<String> handleAuthentication(AuthenticationException ex) {
    return ErrorResponse.error("Unauthenticated");
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ErrorResponse<String> handleAccessDenied(AccessDeniedException ex) {
    return ErrorResponse.error("Forbidden");
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse<String> handleNotFound(NotFoundException ex) {
    return ErrorResponse.error(Objects.requireNonNullElse(ex.getMessage(), "Resource not found"));
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse<String> handleException(Exception ex) {
    return ErrorResponse.error(
        Objects.requireNonNullElse(ex.getMessage(), "Internal Server Error"));
  }
}
