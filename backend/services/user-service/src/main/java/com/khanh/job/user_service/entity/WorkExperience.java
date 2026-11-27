package com.khanh.job.user_service.entity;

import com.khanh.job.domain.BaseEntity;
import com.khanh.job.domain.DisplayOrder;
import com.khanh.job.domain.enums.JobType;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import org.jspecify.annotations.Nullable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = WorkExperience.TABLE)
public class WorkExperience extends BaseEntity {
  public static final String TABLE = "work_experiences";

  @ManyToOne
  @JoinColumn(nullable = false)
  private Resume resume;

  @Column(nullable = false)
  private String companyName;

  private @Nullable String companyLogoUrl;

  @Column(nullable = false)
  private String jobTitle;

  @Enumerated(EnumType.STRING)
  private @Nullable JobType employmentType;

  private @Nullable String location;

  @Column(nullable = false)
  @Builder.Default
  private Boolean isCurrentJob = false;

  private @Nullable String description;

  @ElementCollection(fetch = FetchType.EAGER)
  @Builder.Default
  private List<String> technologies = new ArrayList<>();

  @Embedded private RangeInfo rangeInfo;

  @Embedded private DisplayOrder displayOrder;
}
