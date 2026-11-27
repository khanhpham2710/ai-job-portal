package com.khanh.job.user_service.service;

import com.khanh.job.user_service.entity.Project;
import java.util.List;
import java.util.UUID;

public interface ProjectQueryService {
  List<Project> findByResumeIdOrderByDisplayOrderAsc(UUID resumeId);
}
