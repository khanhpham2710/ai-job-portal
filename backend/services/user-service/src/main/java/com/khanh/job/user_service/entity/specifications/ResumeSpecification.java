package com.khanh.job.user_service.entity.specifications;

import com.khanh.job.user_service.entity.Resume;
import com.khanh.job.user_service.entity.Resume_;
import com.khanh.job.user_service.entity.UserEntity_;
import com.khanh.job.utils.BaseSpecification;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class ResumeSpecification extends BaseSpecification {
  public static Specification<Resume> byDefault(Boolean isDefault) {
    return (root, query, cb) -> cb.equal(root.get(Resume_.IS_DEFAULT), isDefault);
  }

  public static Specification<Resume> byCandidateId(UUID candidateId) {
    return (root, query, cb) ->
        cb.equal(root.get(Resume_.candidate).get(UserEntity_.id), candidateId);
  }
}
