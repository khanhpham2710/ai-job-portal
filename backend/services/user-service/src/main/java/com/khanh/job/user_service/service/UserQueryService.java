package com.khanh.job.user_service.service;

import com.khanh.job.user_service.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

public interface UserQueryService {
  Optional<UserEntity> findByEmail(String email);

  boolean existsByEmail(String email);

  Optional<UserEntity> findById(UUID id);

  @NonNull UserEntity findCurrentUser();
}
