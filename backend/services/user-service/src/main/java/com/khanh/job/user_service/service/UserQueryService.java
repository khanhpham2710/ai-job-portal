package com.khanh.job.user_service.service;

import com.khanh.job.user_service.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;

public interface UserQueryService {
  Optional<UserEntity> findByEmail(String email);

  boolean existsByEmail(String email);

  Optional<UserEntity> findById(UUID id);
}
