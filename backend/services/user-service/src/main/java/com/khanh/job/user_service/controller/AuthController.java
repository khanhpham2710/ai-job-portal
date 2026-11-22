package com.khanh.job.user_service.controller;

import com.khanh.job.common.BaseController;
import com.khanh.job.dto.response.BaseResponse;
import com.khanh.job.user_service.dto.request.LoginRequest;
import com.khanh.job.user_service.dto.request.SignupRequest;
import com.khanh.job.user_service.dto.response.AuthResponse;
import com.khanh.job.user_service.dto.response.LoginResponse;
import com.khanh.job.user_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {
  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<BaseResponse<AuthResponse>> signup(@RequestBody @Valid SignupRequest req)
      throws Exception {
    return ResponseEntity.ok(createSuccessResponse(authService.signup(req)));
  }

  @PostMapping("/login")
  public ResponseEntity<BaseResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest req)
      throws Exception {
    return ResponseEntity.ok(createSuccessResponse(authService.login(req)));
  }

  @PostMapping("/logout")
  public ResponseEntity<BaseResponse<Void>> logout() {
    authService.logout();
    return ResponseEntity.ok(createSuccessResponse(null));
  }
}
