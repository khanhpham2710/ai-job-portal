package com.khanh.job.user_service.entity;

import com.khanh.job.domain.BaseEntity;
import com.khanh.job.domain.enums.ProficiencyLevel;
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
@Table(name = ResumeSkill.TABLE)
public class ResumeSkill extends BaseEntity {
  public static final String TABLE = "resume_skills";

  @ManyToOne
  @JoinColumn(nullable = false)
  private Resume resume;

  @Column(nullable = false)
  private String skillName;

  @Enumerated(EnumType.STRING)
  @Builder.Default
  private @Nullable ProficiencyLevel proficiencyLevel = ProficiencyLevel.BEGINNER;

  private @Nullable Integer yearsOfExperience;

  @Column(nullable = false)
  @ColumnDefault("0")
  @Builder.Default
  private Integer displayOrder = 0;
}
