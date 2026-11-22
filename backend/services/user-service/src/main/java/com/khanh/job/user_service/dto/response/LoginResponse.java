package com.khanh.job.user_service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponse {
  private final String refreshToken;
  private final String accessToken;
  private final UserResponse user;
}
