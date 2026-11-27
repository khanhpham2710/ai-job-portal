package com.khanh.job.user_service.service.impl;

import com.khanh.job.user_service.entity.ResumeSkill;
import com.khanh.job.user_service.entity.ResumeSkill_;
import com.khanh.job.user_service.entity.specifications.ResumeSkillSpecification;
import com.khanh.job.user_service.repository.ResumeSkillRepository;
import com.khanh.job.user_service.service.ResumeSkillQueryService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResumeSkillQueryServiceImpl implements ResumeSkillQueryService {
  private final ResumeSkillRepository resumeSkillRepository;

  @Override
  public List<ResumeSkill> findByResumeIdOrderByDisplayOrderAsc(UUID resumeId) {
    return resumeSkillRepository.findAllActive(
        ResumeSkillSpecification.byResumeId(resumeId),
        Sort.by(Sort.Direction.ASC, ResumeSkill_.DISPLAY_ORDER));
  }
}
