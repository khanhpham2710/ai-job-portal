package com.khanh.job.user_service.entity;

import com.khanh.job.domain.BaseEntity;
import com.khanh.job.domain.enums.ResumeTemplate;
import com.khanh.job.domain.enums.ResumeVisibility;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.jspecify.annotations.Nullable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Resume.TABLE)
public class Resume extends BaseEntity {
  public static final String TABLE = "resumes";

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "candidate_id", nullable = false)
  private UserEntity candidate;

  @Column(nullable = false)
  private String title;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private ResumeTemplate template = ResumeTemplate.PROFESSIONAL;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private ResumeVisibility visibility = ResumeVisibility.PUBLIC;

  @Column(nullable = false)
  @ColumnDefault("false")
  @Builder.Default
  private Boolean isDefault = false;

  @Embedded private @Nullable PersonalInfo personalInfo;

  private @Nullable String summary;

  @Builder.Default private @Nullable Integer completionScore = 0;
}
