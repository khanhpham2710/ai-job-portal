package com.khanh.job.user_service.service.impl;

import com.khanh.job.common.AppUserPrincipal;
import com.khanh.job.exception.ErrorMessage;
import com.khanh.job.exception.NotFoundException;
import com.khanh.job.user_service.dto.response.UserResponse;
import com.khanh.job.user_service.entity.UserEntity;
import com.khanh.job.user_service.mapper.UserMapper;
import com.khanh.job.user_service.repository.UserRepository;
import com.khanh.job.user_service.service.UserQueryService;
import com.khanh.job.user_service.service.UserService;
import com.khanh.job.utils.CurrentUser;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final UserQueryService userQueryService;

  @Override
  public UserResponse getUser() {
    AppUserPrincipal principal = CurrentUser.getCurrentUser();

    if (Objects.isNull(principal)) {
      throw new RuntimeException("Invalid user authentication");
    }

    UserEntity userEntity =
        userQueryService
            .findById(principal.userId())
            .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));

    return userMapper.toDTO(userEntity);
  }

  @Override
  public void deleteUser(UUID userId) {
    UserEntity userEntity =
        userQueryService
            .findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));

    userRepository.softDelete(userEntity);
  }

  @Override
  public void restoreUser(UUID userId) {
    userRepository.restoreById(userId);
  }
}
