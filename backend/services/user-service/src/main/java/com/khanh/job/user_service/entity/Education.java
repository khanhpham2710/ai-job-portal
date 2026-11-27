package com.khanh.job.user_service.entity;

import com.khanh.job.domain.BaseEntity;
import com.khanh.job.domain.DisplayOrder;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.jspecify.annotations.Nullable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = Education.TABLE)
public class Education extends BaseEntity {
  public static final String TABLE = "educations";

  @ManyToOne
  @JoinColumn(nullable = false)
  private Resume resume;

  @Column(nullable = false, length = 200)
  private String institutionName;

  @Column(nullable = false, length = 150)
  private String degree;

  @Column(length = 150)
  private @Nullable String fieldOfStudy;

  @Column(length = 50)
  private @Nullable String grade;

  @Column(nullable = false)
  @Builder.Default
  @ColumnDefault("false")
  private Boolean isCurrentlyStudying = false;

  private @Nullable String description;

  @Embedded private RangeInfo rangeInfo;

  @Embedded private DisplayOrder displayOrder;
}
