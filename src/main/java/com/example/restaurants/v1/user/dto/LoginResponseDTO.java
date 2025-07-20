
package com.example.restaurants.v1.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponseDTO {
  private String token;
  private String message;

  public LoginResponseDTO(String token, String message) {
    this.token = token;
    this.message = message;
  }

  // Getters and setters
}
