package com.khanh.job.user_service.service;

import com.khanh.job.user_service.entity.Resume;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResumeQueryService {
  Optional<Resume> findByCandidateIdAndIsDefault(UUID candidateId, Boolean isDefault);

  List<Resume> findByCandidateId(UUID candidateId);
}
