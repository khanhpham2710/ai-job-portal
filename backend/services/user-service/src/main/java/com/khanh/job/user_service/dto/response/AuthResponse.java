package com.khanh.job.user_service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthResponse {
  private final UserResponse user;
}
