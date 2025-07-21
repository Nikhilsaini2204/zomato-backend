package com.example.restaurants.v1.user.entity;

import com.example.restaurants.v1.user.dto.UserDTO;
import com.example.restaurants.v1.user.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "zomato_user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "NAME")
  private String name;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "PASSWORD")
  private String password;

  @Column(name = "NUMBER")
  private String number;

  @Column(name = "ADDRESS")
  private String address;

  @Column(name = "ROLE")
  @Enumerated(EnumType.STRING)
  private Role role;

  public User(UserDTO user) {
    this.name = user.getName();
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.number = user.getNumber();
    this.address = user.getAddress();
    this.role = Role.CUSTOMER;
  }
}
