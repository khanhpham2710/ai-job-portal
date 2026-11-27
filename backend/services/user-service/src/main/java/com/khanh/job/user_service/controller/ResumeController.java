package com.khanh.job.user_service.controller;

import com.khanh.job.common.BaseController;
import com.khanh.job.dto.response.BaseResponse;
import com.khanh.job.dto.response.user.BaseResumeResponse;
import com.khanh.job.dto.response.user.ResumeResponse;
import com.khanh.job.user_service.dto.request.CreateResumeRequest;
import com.khanh.job.user_service.dto.request.PersonalInfoRequest;
import com.khanh.job.user_service.service.ResumeService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resumes")
@RequiredArgsConstructor
public class ResumeController extends BaseController {

  private final ResumeService resumeService;

  @PostMapping
  public ResponseEntity<BaseResponse<BaseResumeResponse>> createResume(
      @RequestBody @Valid CreateResumeRequest req) {
    return ResponseEntity.ok(createSuccessResponse(resumeService.createResume(req)));
  }

  @GetMapping("/{resumeId}")
  public ResponseEntity<BaseResponse<ResumeResponse>> getResumeById(@PathVariable UUID resumeId)
      throws Exception {
    return ResponseEntity.ok(createSuccessResponse(resumeService.getResumeById(resumeId)));
  }

  @GetMapping("/my")
  public ResponseEntity<BaseResponse<List<BaseResumeResponse>>> getMyResumes() {
    return ResponseEntity.ok(createSuccessResponse(resumeService.getMyResumes()));
  }

  @PutMapping("/{resumeId}/personal-info")
  public ResponseEntity<BaseResponse<BaseResumeResponse>> updatePersonalInfo(
      @PathVariable UUID resumeId, @RequestBody @Valid PersonalInfoRequest req) throws Exception {
    return ResponseEntity.ok(
        createSuccessResponse(resumeService.updatePersonalInfo(resumeId, req)));
  }

  @PatchMapping("/{resumeId}/set-default")
  public ResponseEntity<BaseResponse<BaseResumeResponse>> setDefaultResume(
      @PathVariable UUID resumeId) throws Exception {
    return ResponseEntity.ok(createSuccessResponse(resumeService.setDefaultResume(resumeId)));
  }

  @DeleteMapping("/{resumeId}")
  public ResponseEntity<BaseResponse<String>> deleteResume(@PathVariable UUID resumeId)
      throws Exception {
    resumeService.deleteResume(resumeId);
    return ResponseEntity.ok(createSuccessResponse("Resume deleted successfully"));
  }
}
