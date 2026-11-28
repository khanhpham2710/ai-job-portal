package com.khanh.job.user_service.dto.request;

import java.time.LocalDate;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationRequest {
  private UUID id;
  private String institutionName;
  private String degree;
  private String fieldOfStudy;
  private String grade;
  private LocalDate startDate;
  private LocalDate endDate;
  private Boolean isCurrentlyStudying;
  private String description;
  private Integer displayOrder;
}
