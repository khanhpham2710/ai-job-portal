package com.khanh.job.user_service.service.impl;

import com.khanh.job.common.AppUserPrincipal;
import com.khanh.job.exception.ErrorMessage;
import com.khanh.job.exception.NotFoundException;
import com.khanh.job.user_service.dto.response.UserResponse;
import com.khanh.job.user_service.entity.UserEntity;
import com.khanh.job.user_service.mapper.UserMapper;
import com.khanh.job.user_service.repository.UserRepository;
import com.khanh.job.user_service.service.UserService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public UserResponse getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (Objects.isNull(authentication)) {
      throw new RuntimeException("Invalid user authentication");
    }

    AppUserPrincipal principal = (AppUserPrincipal) authentication.getPrincipal();

    if (Objects.isNull(principal)) {
      throw new RuntimeException("Invalid user authentication");
    }

    UserEntity userEntity =
        userRepository
            .findById(principal.userId())
            .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));

    return userMapper.toDTO(userEntity);
  }
}
