package com.khanh.job.user_service.service;

import com.khanh.job.user_service.entity.ResumeSkill;
import java.util.List;
import java.util.UUID;

public interface ResumeSkillQueryService {
  List<ResumeSkill> findByResumeIdOrderByDisplayOrderAsc(UUID resumeId);
}
