package com.example.restaurants.v1.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  // Handle ResponseStatusException (like your "User already exists" case)
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String, String>> handleResponseStatusException(
      ResponseStatusException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("message", ex.getReason()); // ex.getReason() is your custom message
    return new ResponseEntity<>(response, ex.getStatusCode());
  }

  // Handle all other unexpected exceptions
  //  @ExceptionHandler(Exception.class)
  //  public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
  //    Map<String, String> response = new HashMap<>();
  //    response.put("message", "An unexpected error occurred.");
  //    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  //  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
    // Log full stack trace on server (optional)
    ex.printStackTrace();

    // Send only the message to client
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  public static class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
  }
}
