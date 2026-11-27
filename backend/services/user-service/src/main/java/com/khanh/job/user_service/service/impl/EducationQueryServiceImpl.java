package com.khanh.job.user_service.service.impl;

import com.khanh.job.user_service.entity.Education;
import com.khanh.job.user_service.entity.Language_;
import com.khanh.job.user_service.entity.specifications.EducationSpecification;
import com.khanh.job.user_service.repository.EducationRepository;
import com.khanh.job.user_service.service.EducationQueryService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EducationQueryServiceImpl implements EducationQueryService {
  private final EducationRepository educationRepository;

  @Override
  public List<Education> findByResumeIdOrderByDisplayOrderAsc(UUID resumeId) {
    return educationRepository.findAllActive(
        EducationSpecification.byResumeId(resumeId),
        Sort.by(Sort.Direction.ASC, Language_.DISPLAY_ORDER));
  }
}
