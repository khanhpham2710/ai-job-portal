package com.khanh.job.utils;

import com.khanh.job.common.AppUserPrincipal;
import com.khanh.job.domain.BaseEntity;
import com.khanh.job.domain.BaseEntity_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import java.time.Instant;
import java.util.*;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

public class BaseRepositoryImpl<T extends BaseEntity> extends SimpleJpaRepository<T, UUID>
    implements BaseRepository<T> {

  private final JpaEntityInformation<T, UUID> entityInformation;
  private final EntityManager em;

  public BaseRepositoryImpl(JpaEntityInformation<T, UUID> entityInformation, EntityManager em) {
    super(entityInformation, em);
    this.entityInformation = entityInformation;
    this.em = em;
  }

  // ---------- Đọc bản ghi còn hiệu lực ----------

  @Override
  public List<T> findAllActive() {
    return findAll(BaseSpecification.active());
  }

  @Override
  public List<T> findAllActive(Sort sort) {
    return findAll(BaseSpecification.active(), sort);
  }

  @Override
  public Page<T> findAllActive(Pageable pageable) {
    return findAll(BaseSpecification.active(), pageable);
  }

  @Override
  public List<T> findAllActive(Specification<T> spec) {
    return findAll(spec.and(BaseSpecification.active()));
  }

  @Override
  public List<T> findAllActive(Specification<T> spec, Sort sort) {
    return findAll(spec.and(BaseSpecification.active()), sort);
  }

  @Override
  public Page<T> findAllActive(Specification<T> spec, Pageable pageable) {
    return findAll(spec.and(BaseSpecification.active()), pageable);
  }

  @Override
  public List<T> findAllActiveById(Iterable<UUID> ids) {
    List<UUID> idList = toList(ids);

    return idList.isEmpty()
        ? List.of()
        : findAll(BaseSpecification.<T>byIds(idList).and(BaseSpecification.active()));
  }

  @Override
  public Optional<T> findOneActive(UUID id) {
    Assert.notNull(id, "The given id must not be null!");

    return findOne(BaseSpecification.<T>byIds(List.of(id)).and(BaseSpecification.active()));
  }

  @Override
  public boolean existsActive(UUID id) {
    return findOneActive(id).isPresent();
  }

  // ---------- Đọc bản ghi đã xóa ----------

  @Override
  public List<T> findAllDeleted() {
    return findAll(BaseSpecification.deleted());
  }

  @Override
  public List<T> findAllDeleted(Sort sort) {
    return findAll(BaseSpecification.deleted(), sort);
  }

  @Override
  public Page<T> findAllDeleted(Pageable pageable) {
    return findAll(BaseSpecification.deleted(), pageable);
  }

  @Override
  public List<T> findAllDeletedById(Iterable<UUID> ids) {
    List<UUID> idList = toList(ids);

    return idList.isEmpty()
        ? List.of()
        : findAll(BaseSpecification.<T>byIds(idList).and(BaseSpecification.deleted()));
  }

  @Override
  public Optional<T> findDeletedById(UUID id) {
    Assert.notNull(id, "The given id must not be null!");

    return findOne(BaseSpecification.<T>byIds(List.of(id)).and(BaseSpecification.deleted()));
  }

  @Override
  public long countDeleted() {
    return count(BaseSpecification.deleted());
  }

  @Override
  public boolean existsDeletedById(UUID id) {
    return findDeletedById(id).isPresent();
  }

  // ---------- Xóa mềm ----------

  @Override
  @Transactional
  public void softDelete(UUID id) {
    scheduleSoftDeleteById(id, Instant.now());
  }

  @Override
  @Transactional
  public void softDelete(T entity) {
    Assert.notNull(entity, "The entity must not be null!");

    UUID id = requireId(entity);

    if (changeDeletedAt(List.of(id), Instant.now(), BaseSpecification.active()) == 0) {
      throw notFound("active entity", id);
    }
  }

  @Override
  @Transactional
  public int softDeleteAllById(Iterable<UUID> ids) {
    Set<UUID> idSet = toIdSet(ids);

    return idSet.isEmpty() ? 0 : softDeleteIfActive(idSet);
  }

  @Override
  @Transactional
  public void scheduleSoftDeleteById(UUID id, Instant at) {
    Assert.notNull(id, "The given id must not be null!");
    Assert.notNull(at, "The given instant must not be null!");

    if (changeDeletedAt(List.of(id), at, BaseSpecification.active()) == 0) {
      throw notFound("active entity", id);
    }
  }

  // ---------- Ghi đè delete*: xóa mềm ----------

  @Override
  @Transactional
  public void delete(@NonNull T entity) {
    Assert.notNull(entity, "The entity must not be null!");

    if (entityInformation.isNew(entity)) {
      return;
    }

    softDeleteIfActive(List.of(requireId(entity)));
  }

  @Override
  @Transactional
  public void deleteById(@NonNull UUID id) {
    Assert.notNull(id, "The given id must not be null!");

    softDeleteIfActive(List.of(id));
  }

  @Override
  @Transactional
  public void deleteAll(Iterable<? extends T> entities) {
    Set<UUID> ids = new LinkedHashSet<>();

    for (T entity : entities) {
      Assert.notNull(entity, "The entity must not be null!");

      if (!entityInformation.isNew(entity)) {
        ids.add(requireId(entity));
      }
    }

    if (!ids.isEmpty()) {
      softDeleteIfActive(ids);
    }
  }

  @Override
  @Transactional
  public void deleteAll() {
    changeDeletedAt(BaseSpecification.active(), Instant.now());
  }

  // ---------- Xử lý các phương thức batch ----------

  @Override
  @Transactional
  public void deleteAllInBatch(@NonNull Iterable<T> entities) {
    deleteAll(entities);
  }

  @Override
  @Transactional
  public void deleteAllInBatch() {
    deleteAll();
  }

  @Override
  @Transactional
  public void deleteAllByIdInBatch(@NonNull Iterable<UUID> ids) {
    deleteAllById(ids);
  }

  // ---------- Xóa cứng ----------

  @Override
  @Transactional
  public void hardDelete(T entity) {
    Assert.notNull(entity, "The entity must not be null!");

    em.remove(em.contains(entity) ? entity : em.merge(entity));
  }

  @Override
  @Transactional
  public void hardDeleteById(UUID id) {
    Assert.notNull(id, "The given id must not be null!");

    findById(id).ifPresent(this::hardDelete);
  }

  // ---------- Khôi phục ----------

  @Override
  @Transactional
  public void restoreById(UUID id) {
    Assert.notNull(id, "The given id must not be null!");

    if (changeDeletedAt(List.of(id), null, BaseSpecification.hasDeletedAt()) == 0) {
      throw notFound("deleted entity", id);
    }
  }

  @Override
  @Transactional
  public int restoreAllById(Iterable<UUID> ids) {
    Set<UUID> idSet = toIdSet(ids);

    return idSet.isEmpty() ? 0 : changeDeletedAt(idSet, null, BaseSpecification.hasDeletedAt());
  }

  // ---------- Bulk update ----------

  /**
   * Cập nhật deletedAt bằng một câu UPDATE.
   *
   * <p>newValue = null nghĩa là khôi phục bản ghi.
   */
  private int changeDeletedAt(
      Collection<UUID> ids, @Nullable Instant newValue, Specification<T> requiredState) {
    Specification<T> specification = BaseSpecification.<T>byIds(ids).and(requiredState);

    return changeDeletedAt(specification, newValue);
  }

  /** Cập nhật theo Specification mà không cần danh sách ID. */
  private int changeDeletedAt(Specification<T> specification, @Nullable Instant newValue) {
    em.flush();

    Class<T> type = entityInformation.getJavaType();
    CriteriaBuilder cb = em.getCriteriaBuilder();

    CriteriaUpdate<T> update = cb.createCriteriaUpdate(type);
    Root<T> root = update.from(type);

    Expression<Instant> value =
        newValue == null ? cb.nullLiteral(Instant.class) : cb.literal(newValue);

    AppUserPrincipal userPrincipal = CurrentUser.getCurrentUser();

    update.set(root.<Instant>get(BaseEntity_.DELETED_AT), value);
    update.set(root.<Instant>get(BaseEntity_.UPDATED_AT), cb.literal(Instant.now()));
    if (userPrincipal != null) {
      update.set(root.get(BaseEntity_.UPDATED_BY), userPrincipal.userId().toString());
    }

    CriteriaQuery<Object> query = cb.createQuery();
    Predicate predicate = specification.toPredicate(root, query, cb);
    update.where(predicate);

    int affectedRows = em.createQuery(update).executeUpdate();

    // Bulk update không đồng bộ trạng thái các entity đang được quản lý.
    // Clear để tránh đọc lại dữ liệu cũ từ persistence context.
    em.clear();

    return affectedRows;
  }

  private int softDeleteIfActive(Collection<UUID> ids) {
    return changeDeletedAt(ids, Instant.now(), BaseSpecification.active());
  }

  // ---------- Helpers ----------

  private UUID requireId(T entity) {
    return Objects.requireNonNull(entity.getId(), "entity id");
  }

  private Set<UUID> toIdSet(Iterable<UUID> ids) {
    Set<UUID> result = new LinkedHashSet<>();

    ids.forEach(
        id -> {
          Assert.notNull(id, "The given id must not be null!");
          result.add(id);
        });

    return result;
  }

  private EmptyResultDataAccessException notFound(String what, Object id) {
    return new EmptyResultDataAccessException(
        "No %s %s with id %s exists!".formatted(what, entityInformation.getJavaType(), id), 1);
  }

  private static <E> List<E> toList(Iterable<E> items) {
    List<E> list = new ArrayList<>();
    items.forEach(list::add);
    return list;
  }
}
