package com.khanh.job.user_service.entity;

import com.khanh.job.domain.BaseEntity;
import com.khanh.job.domain.enums.LanguageProficiency;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.jspecify.annotations.Nullable;

@Entity
@Table(name = Language.TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Language extends BaseEntity {
  public static final String TABLE = "languages";

  @ManyToOne
  @JoinColumn(nullable = false)
  private Resume resume;

  @Column(nullable = false)
  private String languageName;

  @Enumerated(EnumType.STRING)
  private @Nullable LanguageProficiency proficiency;

  @Column(nullable = false)
  @ColumnDefault("0")
  @Builder.Default
  private Integer displayOrder = 0;
}
