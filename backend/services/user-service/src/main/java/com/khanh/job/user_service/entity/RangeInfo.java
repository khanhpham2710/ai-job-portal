package com.khanh.job.user_service.entity;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.*;
import org.jspecify.annotations.Nullable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RangeInfo {
  private @Nullable LocalDate startDate;

  private @Nullable LocalDate endDate;
}
