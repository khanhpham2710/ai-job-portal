package com.khanh.job.user_service.entity.specifications;

import com.khanh.job.user_service.entity.Resume_;
import com.khanh.job.user_service.entity.WorkExperience;
import com.khanh.job.user_service.entity.WorkExperience_;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class WorkSpecification {
  public static Specification<WorkExperience> byResumeId(UUID resumeId) {
    return (root, query, cb) ->
        cb.equal(root.get(WorkExperience_.RESUME).get(Resume_.ID), resumeId);
  }
}
