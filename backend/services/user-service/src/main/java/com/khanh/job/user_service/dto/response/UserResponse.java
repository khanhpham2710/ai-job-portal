package com.khanh.job.user_service.dto.response;

import com.khanh.job.domain.enums.UserRole;
import com.khanh.job.domain.enums.UserStatus;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

  private UUID id;
  private String fullName;
  private String email;
  private @Nullable String phone;
  private @Nullable String profileImage;
  private UserRole role;
  private UserStatus status;
  private @Nullable Instant lastLogin;
}
