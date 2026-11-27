package com.khanh.job.user_service.entity;

import com.khanh.job.domain.BaseEntity;
import com.khanh.job.domain.DisplayOrder;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.jspecify.annotations.Nullable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = Project.TABLE)
public class Project extends BaseEntity {
  public static final String TABLE = "projects";

  @ManyToOne
  @JoinColumn(nullable = false)
  private Resume resume;

  @Column(nullable = false)
  private String title;

  private @Nullable String description;

  @ElementCollection(fetch = FetchType.EAGER)
  @Builder.Default
  private List<String> technologies = new ArrayList<>();

  private @Nullable String projectUrl;

  private @Nullable String sourceCodeUrl;

  @Column(nullable = false)
  @Builder.Default
  @ColumnDefault("false")
  private Boolean isOngoing = false;

  @Embedded private RangeInfo rangeInfo;

  @Embedded private DisplayOrder displayOrder;
}
