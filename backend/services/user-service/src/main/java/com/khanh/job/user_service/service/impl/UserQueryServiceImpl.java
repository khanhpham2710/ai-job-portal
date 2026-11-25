package com.khanh.job.user_service.service.impl;

import com.khanh.job.user_service.entity.UserEntity;
import com.khanh.job.user_service.entity.specifications.UserSpecification;
import com.khanh.job.user_service.repository.UserRepository;
import com.khanh.job.user_service.service.UserQueryService;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {
  private final UserRepository userRepository;

  public Optional<UserEntity> findByEmail(String email) {
    return userRepository.findOne(
        UserSpecification.hasEmail(email).and(UserSpecification.active()));
  }

  public boolean existsByEmail(String email) {
    return userRepository.exists(UserSpecification.hasEmail(email));
  }

  public Optional<UserEntity> findById(UUID id) {
    return userRepository.findOneActive(id);
  }
}
