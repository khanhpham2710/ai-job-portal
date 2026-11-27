package com.khanh.job.user_service.service;

import com.khanh.job.user_service.entity.Language;
import java.util.List;
import java.util.UUID;

public interface LanguageQueryService {
  List<Language> findByResumeIdOrderByDisplayOrderAsc(UUID resumeId);
}
