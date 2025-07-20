package com.example.restaurants.v1.user.controller;

import com.example.restaurants.v1.user.dto.ApiResponse;
import com.example.restaurants.v1.user.dto.LoginResponseDTO;
import com.example.restaurants.v1.user.entity.User;
import com.example.restaurants.v1.user.service.UserService;
import com.example.restaurants.v1.user.service.impl.OtpService;
import com.example.restaurants.v1.user.service.impl.SmsService;
import com.example.restaurants.v1.user.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/otp")
public class OtpController {

  @Autowired
  private OtpService otpService;

  @Autowired
  private SmsService smsService;

  @Autowired
  private UserService userService;

  @Autowired
  private JwtUtil jwtUtil;

  // Step 1: Send OTP to phone
  @PostMapping("/send/sms")
  public ResponseEntity<?> sendOtpSms(@RequestParam String phone) {
    String otp = otpService.generateOtp(phone);
    smsService.sendOtp(phone, otp);

    // Build response
    return ResponseEntity.ok(new ApiResponse("OTP" + otp));
  }

  // Step 2: Verify OTP and return JWT
  @PostMapping("/verify")
  public ResponseEntity<?> verifyOtp(@RequestParam String key, @RequestParam String otp) {
    boolean isValid = otpService.validateOtp(key, otp);
    if (!isValid) {
      return ResponseEntity
          .badRequest()
          .body(new ApiResponse("Invalid OTP. Please try again."));
    }

    // Check if user exists or create
    User user = userService.findUserByNumber(key);

    if (user == null) {
      // Return 404 with message
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(new ApiResponse("No account found for this phone number."));
    }
    // Generate JWT token
    String token = jwtUtil.generateToken(user);

    // Return response with token
    LoginResponseDTO response = new LoginResponseDTO(token, "OTP verified successfully");
    return ResponseEntity.ok(response);

  }

  @PostMapping("/verifyotp")
  public ResponseEntity<?> verifyOnlyOtp(@RequestParam String key, @RequestParam String otp) {
    boolean isValid = otpService.validateOtp(key, otp);
    if (!isValid) {
      // OTP invalid
      return ResponseEntity
          .badRequest()
          .body(new ApiResponse("Invalid OTP. Please try again."));
    }

    // OTP valid
    return ResponseEntity.ok(new ApiResponse("OTP verified successfully"));
  }


}
