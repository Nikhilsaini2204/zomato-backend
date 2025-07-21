package com.example.restaurants.v1.user.controller;

import com.example.restaurants.v1.user.dto.LoginResponseDTO;
import com.example.restaurants.v1.user.dto.UserDTO;
import com.example.restaurants.v1.user.entity.User;
import com.example.restaurants.v1.user.service.UserService;
import com.example.restaurants.v1.user.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/v1/user")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private JwtUtil jwtUtil;

  @PostMapping("/create")
  public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) {
    //    UserDTO user = userService.saveUser(userDTO);
    //    return new ResponseEntity<>(user, HttpStatus.OK);
    UserDTO savedUser = userService.saveUser(userDTO);
    User user = new User(savedUser);
    // Generate JWT token for the saved user
    String token = jwtUtil.generateToken(user);

    // Build response with token and success message
    LoginResponseDTO response = new LoginResponseDTO(token, "User created successfully");

    return ResponseEntity.ok(response);
  }

  @PostMapping("/owner")
  public ResponseEntity<UserDTO> updateUser(@RequestParam String userId) {
    UserDTO user = userService.updateRole(userId);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> getUsersByFilters(
      @RequestParam(required = false) Long userId, @RequestParam(required = false) String role) {
    long userIdFilter = (userId != null) ? userId : 0L;
    String roleFilter = (role != null) ? role : "";

    List<UserDTO> users = userService.getUserByFilters(userIdFilter, roleFilter);
    return ResponseEntity.ok(users);
  }

  @GetMapping("/profile")
  public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String authHeader) {
    // Remove "Bearer " prefix
    String token = authHeader.replace("Bearer ", "");

    // Extract phone from JWT
    String phone = jwtUtil.extractPhone(token);

    // Fetch user details using phone
    User user = userService.findUserByNumber(phone);

    if (user == null) {
      return ResponseEntity.notFound().build();
    }

    UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getNumber(),
        user.getAddress(), user.getRole().name());
    return ResponseEntity.ok(userDTO);
  }

}
