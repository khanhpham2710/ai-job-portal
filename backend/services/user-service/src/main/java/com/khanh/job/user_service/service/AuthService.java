package com.khanh.job.user_service.service;

import com.khanh.job.user_service.dto.request.LoginRequest;
import com.khanh.job.user_service.dto.request.SignupRequest;
import com.khanh.job.user_service.dto.response.AuthResponse;
import com.khanh.job.user_service.dto.response.LoginResponse;

public interface AuthService {
  AuthResponse signup(SignupRequest req) throws Exception;

  LoginResponse login(LoginRequest req) throws Exception;

  void logout();
}
