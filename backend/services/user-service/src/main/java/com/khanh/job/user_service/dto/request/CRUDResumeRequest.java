package com.khanh.job.user_service.dto.request;

import com.khanh.job.domain.enums.ResumeTemplate;
import com.khanh.job.domain.enums.ResumeVisibility;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CRUDResumeRequest {
  @NotBlank(message = "Resume title is required")
  private String title;

  private ResumeTemplate template;

  private ResumeVisibility visibility;

  private Boolean isDefault;

  private String summary;

  private List<WorkExperienceRequest> workExperiences;
  private List<EducationRequest> educations;
  private List<ResumeSkillRequest> skills;
  private List<ProjectRequest> projects;
  private List<LanguageRequest> languages;
}
