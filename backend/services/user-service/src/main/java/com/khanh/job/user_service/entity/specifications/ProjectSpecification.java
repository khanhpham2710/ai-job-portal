package com.khanh.job.user_service.entity.specifications;

import com.khanh.job.user_service.entity.*;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecification {
  public static Specification<Project> byResumeId(UUID resumeId) {
    return (root, query, cb) -> cb.equal(root.get(Project_.RESUME).get(Resume_.ID), resumeId);
  }
}
