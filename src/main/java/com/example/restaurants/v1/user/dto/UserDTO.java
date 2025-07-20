package com.example.restaurants.v1.user.dto;

import com.example.restaurants.v1.user.entity.User;
import com.example.restaurants.v1.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  private Long id;
  private String name;
  private String email;
  private String password;
  private String number;
  private String address;
  private Role role;

  public UserDTO(User user){
    this.id = user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.number = user.getNumber();
    this.address = user.getAddress();
    this.role = Role.CUSTOMER;
  }

  public UserDTO(Long id, String name, String email,String number,  String address, String role){
    this.id = id;
    this.name = name;
    this.number = number;
    this.role = Role.valueOf(role);
    this.email = email;
    this.address = address;
  }

}
