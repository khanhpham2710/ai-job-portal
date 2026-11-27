package com.khanh.job.user_service.service.impl;

import com.khanh.job.user_service.entity.Resume;
import com.khanh.job.user_service.entity.specifications.ResumeSpecification;
import com.khanh.job.user_service.repository.ResumeRepository;
import com.khanh.job.user_service.service.ResumeQueryService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResumeQueryServiceImpl implements ResumeQueryService {
  private final ResumeRepository resumeRepository;

  @Override
  public Optional<Resume> findByCandidateIdAndIsDefault(UUID candidateId, Boolean isDefault) {
    return resumeRepository.findOne(
        ResumeSpecification.byDefault(isDefault).and(ResumeSpecification.active()));
  }

  @Override
  public List<Resume> findByCandidateId(UUID candidateId) {
    return resumeRepository.findAllActive(
        ResumeSpecification.byCandidateId(candidateId).and(ResumeSpecification.active()));
  }
}
