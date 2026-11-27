package com.khanh.job.dto.response.user;

import com.khanh.job.domain.enums.JobType;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkExperienceResponse {

  private UUID id;
  private String companyName;
  private String companyLogoUrl;
  private String jobTitle;
  private JobType employmentType;
  private String location;
  private LocalDate startDate;
  private LocalDate endDate;
  private Boolean isCurrentJob;
  private String description;
  private List<String> technologies;
  private Integer displayOrder;
}
