package com.khanh.job.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisplayOrder {
  @Column(nullable = false)
  @ColumnDefault("0")
  @Builder.Default
  private Integer displayOrder = 0;
}
