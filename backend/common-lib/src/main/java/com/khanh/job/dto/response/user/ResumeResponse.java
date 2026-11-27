package com.khanh.job.dto.response.user;

import java.util.List;
import lombok.*;
import org.jspecify.annotations.Nullable;

@Getter
@Setter
@NoArgsConstructor
public class ResumeResponse extends BaseResumeResponse {
  private @Nullable List<WorkExperienceResponse> workExperiences;
  private @Nullable List<EducationResponse> educations;
  private @Nullable List<ResumeSkillResponse> skills;
  private @Nullable List<ProjectResponse> projects;
  private @Nullable List<LanguageResponse> languages;
}
