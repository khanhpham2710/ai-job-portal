package com.khanh.job.dto.response.user;

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
public class ProjectResponse {
  private UUID id;
  private String title;
  private String description;
  private List<String> technologies;
  private String projectUrl;
  private String sourceCodeUrl;
  private LocalDate startDate;
  private LocalDate endDate;
  private Boolean isOngoing;
  private Integer displayOrder;
}
