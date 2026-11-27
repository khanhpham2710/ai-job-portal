package com.khanh.job.dto.response.user;

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
public class ResumeSkillResponse {
  private UUID id;
  private String skillName;
  private ProficiencyLevel proficiencyLevel;
  private Integer yearsOfExperience;
  private Integer displayOrder;
}
