package com.khanh.job.user_service.service;

import com.khanh.job.user_service.entity.WorkExperience;
import java.util.List;
import java.util.UUID;

public interface WorkExperienceQueryService {
  List<WorkExperience> findByResumeIdOrderByDisplayOrderAsc(UUID resumeId);
}
