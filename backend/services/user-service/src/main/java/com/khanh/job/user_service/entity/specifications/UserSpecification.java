package com.khanh.job.user_service.entity.specifications;

import com.khanh.job.user_service.entity.UserEntity;
import com.khanh.job.user_service.entity.UserEntity_;
import com.khanh.job.utils.BaseSpecification;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification extends BaseSpecification {
  public static Specification<UserEntity> hasEmail(@NonNull String email) {
    return (root, query, cb) -> cb.equal(root.get(UserEntity_.EMAIL), email);
  }
}
