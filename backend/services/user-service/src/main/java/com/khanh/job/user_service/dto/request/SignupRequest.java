package com.khanh.job.user_service.dto.request;

import com.khanh.job.domain.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import org.jspecify.annotations.Nullable;

@Data
public class SignupRequest {

  @NotBlank(message = "full name is mandatory")
  private @NonNull String fullName;

  @Email(message = "Email should be valid")
  @NotBlank(message = "Email is mandatory")
  private @NonNull String email;

  @NotBlank(message = "Password is mandatory")
  private @NonNull String password;

  private @Nullable String phone;

  @NotNull(message = "Role is mandatory")
  private @NonNull UserRole role;
}
