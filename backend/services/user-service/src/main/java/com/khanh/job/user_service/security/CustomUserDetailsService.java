package com.khanh.job.user_service.security;

import com.khanh.job.user_service.entity.UserEntity;
import com.khanh.job.user_service.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public @NonNull UserDetails loadUserByUsername(@NonNull String username)
      throws UsernameNotFoundException {

    Optional<UserEntity> user = userRepository.findByEmail(username);

    if (user.isEmpty()) {
      throw new UsernameNotFoundException("user not found " + username);
    }

    UserEntity userEntity = user.get();

    return userEntity.toUserDetail();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
