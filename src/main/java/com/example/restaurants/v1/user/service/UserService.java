package com.example.restaurants.v1.user.service;

import com.example.restaurants.v1.user.dto.UserDTO;
import com.example.restaurants.v1.user.entity.User;

import java.util.List;

public interface UserService {
  UserDTO saveUser(UserDTO userDTO);
  List<UserDTO> getUserByFilters(Long userId, String role);
  User findUserByNumber(String key);
}
