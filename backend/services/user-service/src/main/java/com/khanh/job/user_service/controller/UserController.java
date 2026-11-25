package com.khanh.job.user_service.controller;

import com.khanh.job.common.BaseController;
import com.khanh.job.dto.response.BaseResponse;
import com.khanh.job.user_service.dto.response.UserResponse;
import com.khanh.job.user_service.service.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController extends BaseController {
  private final UserService userService;

  @GetMapping
  public ResponseEntity<BaseResponse<UserResponse>> getUser() {
    return ResponseEntity.ok(createSuccessResponse(userService.getUser()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<BaseResponse<Void>> deleteUser(@PathVariable UUID id) {
    userService.deleteUser(id);
    return ResponseEntity.ok(createSuccessResponse(null));
  }

  @PostMapping("/restore/{id}")
  public ResponseEntity<BaseResponse<Void>> restoreUser(@PathVariable UUID id) {
    userService.restoreUser(id);
    return ResponseEntity.ok(createSuccessResponse(null));
  }
}
