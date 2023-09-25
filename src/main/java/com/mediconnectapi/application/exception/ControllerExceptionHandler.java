package com.mediconnectapi.application.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ResponseBody
@ControllerAdvice
public class ControllerExceptionHandler {

  @ResponseStatus(value = HttpStatus.CONFLICT)
  @ExceptionHandler(value = DataIntegrityViolationException.class)
  public ErrorMessage dataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex, WebRequest webRequest) {
    return new ErrorMessage(
        HttpStatus.CONFLICT.value(),
        new Date(),
        ex.getMessage(),
        webRequest.getDescription(false)
    );
  }

  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(BadCredentialsException.class)
  public ErrorMessage badCredentialsExceptionHandler(BadCredentialsException ex, WebRequest webRequest) {
    return new ErrorMessage(
        HttpStatus.UNAUTHORIZED.value(),
        new Date(),
        ex.getMessage(),
        webRequest.getDescription(false)
    );
  }
}
