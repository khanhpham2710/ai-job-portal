package com.khanh.job.user_service.service;

import com.khanh.job.user_service.dto.response.UserResponse;
import java.util.UUID;

public interface UserService {
  public UserResponse getUser();

  public void deleteUser(UUID userId);

  public void restoreUser(UUID userId);
}
