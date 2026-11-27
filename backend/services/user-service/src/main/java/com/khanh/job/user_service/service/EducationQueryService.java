package com.khanh.job.user_service.service;

import com.khanh.job.user_service.entity.Education;
import java.util.List;
import java.util.UUID;

public interface EducationQueryService {
  List<Education> findByResumeIdOrderByDisplayOrderAsc(UUID resumeId);
}
