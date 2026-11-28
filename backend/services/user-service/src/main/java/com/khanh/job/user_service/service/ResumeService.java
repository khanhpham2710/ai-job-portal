package com.khanh.job.user_service.service;

import com.khanh.job.dto.response.user.BaseResumeResponse;
import com.khanh.job.dto.response.user.ResumeResponse;
import com.khanh.job.user_service.dto.request.CRUDResumeRequest;
import com.khanh.job.user_service.dto.request.PersonalInfoRequest;
import java.util.List;
import java.util.UUID;

public interface ResumeService {
  BaseResumeResponse createResume(CRUDResumeRequest req);

  BaseResumeResponse updateResume(UUID resumeId, CRUDResumeRequest req) throws Exception;

  ResumeResponse getResumeById(UUID resumeId) throws Exception;

  List<BaseResumeResponse> getMyResumes();

  BaseResumeResponse updatePersonalInfo(UUID resumeId, PersonalInfoRequest req) throws Exception;

  BaseResumeResponse setDefaultResume(UUID resumeId) throws Exception;

  void deleteResume(UUID resumeId) throws Exception;
}
