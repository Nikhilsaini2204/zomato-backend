package com.example.restaurants.v1.user.service.impl;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// SMS Service (Using Twilio)
@Service
public class SmsService {

  @Value("${twilio.account.sid}")
  private String accountSid;

  @Value("${twilio.auth.token}")
  private String authToken;

  @Value("${twilio.phone.number}")
  private String twilioPhoneNumber;

  public void sendOtp(String phoneNumber, String otp) {
    try {
      System.out.println("SID: " + accountSid);
      System.out.println("Auth Token: " + authToken);
      System.out.println("Twilio Number: " + twilioPhoneNumber);

      Twilio.init(accountSid, authToken);

      Message.creator(new com.twilio.type.PhoneNumber("+" + phoneNumber),
          new com.twilio.type.PhoneNumber(twilioPhoneNumber), "Your OTP code is: " + otp).create();

      System.out.println("SMS sent successfully!");
    } catch (Exception e) {
      System.err.println("Twilio API error: " + e.getMessage());
      e.printStackTrace();
    }
  }


}

