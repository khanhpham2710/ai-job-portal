package com.khanh.job.user_service.service.impl;

import com.khanh.job.user_service.entity.WorkExperience;
import com.khanh.job.user_service.entity.WorkExperience_;
import com.khanh.job.user_service.entity.specifications.WorkSpecification;
import com.khanh.job.user_service.repository.WorkExperienceRepository;
import com.khanh.job.user_service.service.WorkExperienceQueryService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkExperienceQueryServiceImpl implements WorkExperienceQueryService {
  private final WorkExperienceRepository workExperienceRepository;

  @Override
  public List<WorkExperience> findByResumeIdOrderByDisplayOrderAsc(UUID resumeId) {
    return workExperienceRepository.findAllActive(
        WorkSpecification.byResumeId(resumeId),
        Sort.by(Sort.Direction.ASC, WorkExperience_.DISPLAY_ORDER));
  }
}
