package com.khanh.job.user_service.dto.dao;

import com.khanh.job.user_service.entity.*;
import java.util.List;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ResumeDAO {
  private List<ResumeSkill> resumeSkills;
  private List<Education> educations;
  private List<Project> projects;
  private List<Language> languages;
  private List<WorkExperience> workExperiences;
}
