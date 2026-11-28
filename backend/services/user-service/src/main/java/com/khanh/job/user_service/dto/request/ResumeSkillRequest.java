package com.khanh.job.user_service.dto.request;

import com.khanh.job.domain.enums.ProficiencyLevel;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResumeSkillRequest {
  private UUID id;
  private String skillName;
  private ProficiencyLevel proficiencyLevel;
  private Integer yearsOfExperience;
  private Integer displayOrder;
}
