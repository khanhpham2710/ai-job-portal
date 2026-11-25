package com.khanh.job.utils;

import com.khanh.job.domain.BaseEntity;
import com.khanh.job.domain.BaseEntity_;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class BaseSpecification {

  private static final String ID_FIELD = BaseEntity_.ID;

  private static final String DELETED_FIELD = BaseEntity_.DELETED_AT;

  /** Còn hiệu lực: - Chưa bị xóa. - Hoặc được lên lịch xóa trong tương lai. */
  public static <T extends BaseEntity> Specification<T> active() {
    return (root, query, cb) -> {
      Instant now = Instant.now();

      return cb.or(
          cb.isNull(root.<Instant>get(DELETED_FIELD)),
          cb.greaterThan(root.get(DELETED_FIELD), now));
    };
  }

  /** Đã xóa: deletedAt khác null và đã đến thời điểm xóa. */
  public static <T extends BaseEntity> Specification<T> deleted() {
    return (root, query, cb) -> {
      Instant now = Instant.now();

      return cb.and(
          cb.isNotNull(root.<Instant>get(DELETED_FIELD)),
          cb.lessThanOrEqualTo(root.<Instant>get(DELETED_FIELD), now));
    };
  }

  /** Có deletedAt, bao gồm cả bản ghi được lên lịch xóa trong tương lai. */
  public static <T extends BaseEntity> Specification<T> hasDeletedAt() {
    return (root, query, cb) -> cb.isNotNull(root.<Instant>get(DELETED_FIELD));
  }

  /** Lọc theo danh sách UUID. */
  public static <T extends BaseEntity> Specification<T> byIds(Collection<UUID> ids) {
    return (root, query, cb) -> root.get(ID_FIELD).in(ids);
  }
}
