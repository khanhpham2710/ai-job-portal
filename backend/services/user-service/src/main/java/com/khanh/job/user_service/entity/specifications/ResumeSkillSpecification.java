package com.khanh.job.user_service.entity.specifications;

import com.khanh.job.user_service.entity.*;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class ResumeSkillSpecification {
  public static Specification<ResumeSkill> byResumeId(UUID resumeId) {
    return (root, query, cb) -> cb.equal(root.get(ResumeSkill_.RESUME).get(Resume_.ID), resumeId);
  }
}
