package com.example.restaurants.v1.user.dto;


public class ApiResponse {
  private String message;

  public ApiResponse(String message) {
    this.message = message;
  }

  // Getter and Setter
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
