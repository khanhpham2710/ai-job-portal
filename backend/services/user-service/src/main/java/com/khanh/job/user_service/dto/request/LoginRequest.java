package com.khanh.job.user_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
public class LoginRequest {

  @Email(message = "Email should be valid")
  @NotBlank(message = "Email is mandatory")
  private @NonNull String email;

  @NotBlank(message = "Password is mandatory")
  private @NonNull String password;
}
