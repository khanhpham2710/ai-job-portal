package com.khanh.job.mapper;

import com.khanh.job.domain.DisplayOrder;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GlobalEmbeddableMapper {
  default @Nullable Integer map(DisplayOrder value) {
    return value == null ? null : value.getDisplayOrder();
  }

  default @Nullable DisplayOrder map(@Nullable Integer value) {
    return value == null ? null : new DisplayOrder(value);
  }
}
