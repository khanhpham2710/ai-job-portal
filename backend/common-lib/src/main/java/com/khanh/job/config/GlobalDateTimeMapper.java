package com.khanh.job.config;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GlobalDateTimeMapper {

  default @Nullable LocalDateTime map(Instant value) {
    return value == null ? null : LocalDateTime.ofInstant(value, ZoneOffset.UTC);
  }

  default @Nullable Instant map(LocalDateTime value) {
    return value == null ? null : value.toInstant(ZoneOffset.UTC);
  }

  default @Nullable LocalDate mapToLocalData(LocalDateTime value) {
    return value == null ? null : value.toLocalDate();
  }
}
