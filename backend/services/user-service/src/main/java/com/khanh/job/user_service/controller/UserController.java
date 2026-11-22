package com.khanh.job.user_service.controller;

import com.khanh.job.common.BaseController;
import com.khanh.job.dto.response.BaseResponse;
import com.khanh.job.user_service.dto.response.UserResponse;
import com.khanh.job.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController extends BaseController {
  private final UserService userService;

  @GetMapping
  public ResponseEntity<BaseResponse<UserResponse>> getUser() {
    return ResponseEntity.ok(createSuccessResponse(userService.getUser()));
  }
}
