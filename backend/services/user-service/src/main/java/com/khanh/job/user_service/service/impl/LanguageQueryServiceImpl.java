package com.khanh.job.user_service.service.impl;

import com.khanh.job.user_service.entity.Language;
import com.khanh.job.user_service.entity.Language_;
import com.khanh.job.user_service.entity.specifications.LanguageSpecification;
import com.khanh.job.user_service.repository.LanguageRepository;
import com.khanh.job.user_service.service.LanguageQueryService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LanguageQueryServiceImpl implements LanguageQueryService {
  private final LanguageRepository languageRepository;

  @Override
  public List<Language> findByResumeIdOrderByDisplayOrderAsc(UUID resumeId) {
    return languageRepository.findAllActive(
        LanguageSpecification.byResumeId(resumeId),
        Sort.by(Sort.Direction.ASC, Language_.DISPLAY_ORDER));
  }
}
