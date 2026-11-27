package com.khanh.job.utils;

import com.khanh.job.domain.BaseEntity;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity>
    extends JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {
  List<T> findAllActive();

  List<T> findAllActive(Sort sort);

  Page<T> findAllActive(Pageable pageable);

  List<T> findAllActive(Specification<T> spec);

  List<T> findAllActive(Specification<T> spec, Sort sort);

  Page<T> findAllActive(Specification<T> spec, Pageable pageable);

  List<T> findAllActiveById(Iterable<UUID> ids);

  Optional<T> findOneActive(UUID id);

  boolean existsActive(UUID id);

  List<T> findAllDeleted();

  List<T> findAllDeleted(Sort sort);

  Page<T> findAllDeleted(Pageable pageable);

  List<T> findAllDeletedById(Iterable<UUID> ids);

  Optional<T> findDeletedById(UUID id);

  long countDeleted();

  boolean existsDeletedById(UUID id);

  void softDelete(UUID id);

  void softDelete(T entity);

  int softDeleteAllById(Iterable<UUID> ids);

  void scheduleSoftDeleteById(UUID id, Instant at);

  void hardDelete(T entity);

  void hardDeleteById(UUID id);

  void restoreById(UUID id);

  /** Trả về số bản ghi thực sự được khôi phục. Cũng huỷ luôn lịch xóa hẹn giờ. */
  int restoreAllById(Iterable<UUID> ids);
}
