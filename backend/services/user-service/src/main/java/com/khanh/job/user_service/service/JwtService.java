package com.khanh.job.user_service.service;

import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

  String generateAccessToken(UUID userId, UserDetails userDetails);

  String generateRefreshToken(UUID userId);

  UUID parseRefreshToken(String token);
}
