package com.example.restaurants.v1.user.service.impl;

import com.example.restaurants.v1.user.dto.UserDTO;
import com.example.restaurants.v1.user.entity.User;
import com.example.restaurants.v1.user.enums.Role;
import com.example.restaurants.v1.user.repository.UserRepository;
import com.example.restaurants.v1.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDTO saveUser(UserDTO userDTO) {
    Optional<User> existingByPhone = userRepository.findByNumber(userDTO.getNumber());
    if (existingByPhone.isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this phone number already exists");
    }

    Optional<User> existingByEmail = userRepository.findByEmail(userDTO.getEmail());
    if (existingByEmail.isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this email already exists");
    }

    User user = new User(userDTO);
    user = userRepository.save(user);

    return new UserDTO(user);
  }



  @Override
  public List<UserDTO> getUserByFilters(Long userId, String role){
    List<User> users;

    if (userId > 0 && role != null && !role.isEmpty()) {
      users = userRepository.findByIdAndRole(userId, Role.valueOf(role.toUpperCase()));
    } else if (userId > 0) {
      users = userRepository.findById(userId)
          .map(List::of)
          .orElse(List.of());
    } else if (role != null && !role.isEmpty()) {
      users = userRepository.findByRole(Role.valueOf(role.toUpperCase()));
    } else {
      users = userRepository.findAll();
    }

    return users.stream()
        .map(UserDTO::new)
        .toList();
  }

  @Override
  public User findUserByNumber(String key){
    if (key.startsWith("91")) {
      key = key.substring(2);
    }
    User user = userRepository.findByNumber(key).orElse(null);
    return user;
  }

}