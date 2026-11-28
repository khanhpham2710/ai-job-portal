package com.khanh.job.user_service.service.impl;

import com.khanh.job.domain.enums.UserRole;
import com.khanh.job.domain.enums.UserStatus;
import com.khanh.job.exception.ErrorMessage;
import com.khanh.job.exception.NotFoundException;
import com.khanh.job.user_service.dto.request.LoginRequest;
import com.khanh.job.user_service.dto.request.SignupRequest;
import com.khanh.job.user_service.dto.response.AuthResponse;
import com.khanh.job.user_service.dto.response.LoginResponse;
import com.khanh.job.user_service.entity.UserEntity;
import com.khanh.job.user_service.mapper.UserMapper;
import com.khanh.job.user_service.repository.UserRepository;
import com.khanh.job.user_service.security.CustomUserDetailsService;
import com.khanh.job.user_service.service.AuthService;
import com.khanh.job.user_service.service.JwtService;
import com.khanh.job.user_service.service.UserQueryService;
import com.khanh.job.utils.UUIDGenerator;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final CustomUserDetailsService customUserDetailsService;
  private final UserQueryService userQueryService;

  private final UserMapper userMapper;

  @Override
  @Transactional
  public AuthResponse signup(SignupRequest req) throws Exception {

    if (userQueryService.existsByEmail(req.getEmail())) {
      throw new Exception(ErrorMessage.USER_ALREADY_EXISTS);
    }

    if (req.getRole() == UserRole.ROLE_ADMIN) {
      throw new Exception("cannot self-register as role admin");
    }

    String encodedPassword =
        Objects.requireNonNull(
            passwordEncoder.encode(req.getPassword()), "Encoded password must not be null");

    UUID userId = UUIDGenerator.generateUUID();

    setUserIdToRequestContext(userId);

    UserEntity user =
        UserEntity.builder()
            .fullName(req.getFullName())
            .email(req.getEmail())
            .password(encodedPassword)
            .role(req.getRole())
            .phone(req.getPhone())
            .lastLogin(Instant.now())
            .status(UserStatus.ACTIVE)
            .build();

    user.setId(userId);

    UserEntity savedUser = userRepository.add(user);

    return AuthResponse.builder().user(userMapper.toResponse(savedUser)).build();
  }

  @Override
  @Transactional
  public LoginResponse login(LoginRequest req) throws Exception {
    UserEntity user =
        userQueryService
            .findByEmail(req.getEmail())
            .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));

    Authentication authentication = authenticate(req.getEmail(), req.getPassword());

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String accessToken = jwtService.generateAccessToken(user.getId(), user.toUserDetail());
    String refreshToken = jwtService.generateRefreshToken(user.getId());

    user.setLastLogin(Instant.now());
    setUserIdToRequestContext(user.getId());
    userRepository.update(user);

    return LoginResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .user(userMapper.toResponse(user))
        .build();
  }

  public void logout() {}

  private Authentication authenticate(String email, String password) throws Exception {
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
      throw new Exception("Invalid password");
    }
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }

  private void setUserIdToRequestContext(UUID userId) {
    var requestAttributes = RequestContextHolder.getRequestAttributes();

    if (requestAttributes instanceof ServletRequestAttributes attributes) {
      attributes.getRequest().setAttribute("userId", userId.toString());
    }
  }
}
