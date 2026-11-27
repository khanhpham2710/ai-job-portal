package com.khanh.job.user_service.service.impl;

import com.khanh.job.common.AppUserPrincipal;
import com.khanh.job.exception.ErrorMessage;
import com.khanh.job.exception.NotFoundException;
import com.khanh.job.user_service.entity.UserEntity;
import com.khanh.job.user_service.entity.specifications.UserSpecification;
import com.khanh.job.user_service.repository.UserRepository;
import com.khanh.job.user_service.service.UserQueryService;
import com.khanh.job.utils.CurrentUser;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
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

  @Override
  public @NonNull UserEntity findCurrentUser() throws NotFoundException {
    AppUserPrincipal currentUser = CurrentUser.getCurrentUser();

    if (currentUser == null) {
      throw new NotFoundException(ErrorMessage.USER_NOT_FOUND);
    }

    return this.findById(currentUser.userId())
        .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));
  }
}
