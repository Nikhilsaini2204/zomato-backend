package com.example.restaurants.v1.user.utils;

import com.example.restaurants.v1.user.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

  private static final String SECRET_KEY_BASE64 =
      "o4Kd8CST0Z7Xj4b+8cQp3d13uBa7YpAk3nTI7KHTyBvO+eAg3bdhS+9eJ3TxR0M3LGkMKH2GjM+5FBCBISACZw==";

  private static final SecretKey SECRET_KEY =
      Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY_BASE64));

  private final long EXPIRATION_TIME = 86400000; // 24 hours

  public String generateToken(User user) {
    return Jwts.builder().setSubject(user.getNumber()).claim("role", user.getRole().name())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
  }

  public String extractPhone(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody()
        .getSubject();
  }

  public String extractClaim(String token, String claimKey) {
    return Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody()
        .get(claimKey, String.class);
  }

}
