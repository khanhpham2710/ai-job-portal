package com.khanh.job.user_service.entity.specifications;

import com.khanh.job.user_service.entity.Language;
import com.khanh.job.user_service.entity.Language_;
import com.khanh.job.user_service.entity.Resume_;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class LanguageSpecification {
  public static Specification<Language> byResumeId(UUID resumeId) {
    return (root, query, cb) -> cb.equal(root.get(Language_.RESUME).get(Resume_.ID), resumeId);
  }
}
