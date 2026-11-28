package com.khanh.job.user_service.mapper;

import com.khanh.job.config.GlobalMapperConfig;
import com.khanh.job.dto.response.user.*;
import com.khanh.job.user_service.dto.request.*;
import com.khanh.job.user_service.entity.*;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.mapstruct.*;

@Mapper(config = GlobalMapperConfig.class)
public interface ResumeMapper {
  @Mapping(target = "candidateId", source = "candidate.id")
  BaseResumeResponse toBaseResponse(@NonNull Resume resume);

  @Mapping(target = "candidateId", source = "resume.candidate.id")
  ResumeResponse toResponse(
      @NonNull Resume resume,
      @NonNull List<WorkExperienceResponse> workExperiences,
      @NonNull List<EducationResponse> educations,
      @NonNull List<ResumeSkillResponse> skills,
      @NonNull List<ProjectResponse> projects,
      @NonNull List<LanguageResponse> languages);

  PersonalInfo toPersonalInfo(@NonNull PersonalInfoRequest request);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void update(@NonNull PersonalInfoRequest request, @MappingTarget @NonNull PersonalInfo target);

  @NonNull
  default PersonalInfo merge(@NonNull PersonalInfoRequest request, @Nullable PersonalInfo target) {
    if (target == null) {
      return toPersonalInfo(request);
    }

    update(request, target);
    return target;
  }

  ResumeSkillResponse toSkillResponse(ResumeSkill skill);

  @Mapping(target = "startDate", source = "rangeInfo.startDate")
  @Mapping(target = "endDate", source = "rangeInfo.endDate")
  EducationResponse toEducationResponse(Education edu);

  @Mapping(target = "startDate", source = "rangeInfo.startDate")
  @Mapping(target = "endDate", source = "rangeInfo.endDate")
  ProjectResponse toProjectResponse(Project project);

  LanguageResponse toLanguageResponse(Language lang);

  @Mapping(target = "startDate", source = "rangeInfo.startDate")
  @Mapping(target = "endDate", source = "rangeInfo.endDate")
  WorkExperienceResponse toWorkExperienceResponse(WorkExperience exp);

  @Mapping(target = "rangeInfo.startDate", source = "startDate")
  @Mapping(target = "rangeInfo.endDate", source = "endDate")
  Education toEducation(EducationRequest request);

  ResumeSkill toSkill(ResumeSkillRequest requests);

  @Mapping(target = "rangeInfo.startDate", source = "startDate")
  @Mapping(target = "rangeInfo.endDate", source = "endDate")
  Project toProject(ProjectRequest request);

  Language toLanguage(LanguageRequest request);

  @Mapping(target = "rangeInfo.startDate", source = "startDate")
  @Mapping(target = "rangeInfo.endDate", source = "endDate")
  WorkExperience toWorkExperience(WorkExperienceRequest request);

  List<ResumeSkill> toSkills(List<ResumeSkillRequest> requests);

  List<Education> toEducations(List<EducationRequest> requests);

  List<Project> toProjects(List<ProjectRequest> requests);

  List<Language> toLanguages(List<LanguageRequest> requests);

  List<WorkExperience> toWorkExperiences(List<WorkExperienceRequest> requests);

  @InheritConfiguration(name = "toWorkExperience")
  void updateWorkExperience(
      @NonNull WorkExperienceRequest request, @MappingTarget @NonNull WorkExperience target);

  @InheritConfiguration(name = "toSkill")
  void updateSkill(@NonNull ResumeSkillRequest request, @MappingTarget @NonNull ResumeSkill target);

  @InheritConfiguration(name = "toLanguage")
  void updateLanguage(@NonNull LanguageRequest request, @MappingTarget @NonNull Language target);

  @InheritConfiguration(name = "toProject")
  void updateProject(@NonNull ProjectRequest request, @MappingTarget @NonNull Project target);

  @InheritConfiguration(name = "toEducation")
  void updateEducation(@NonNull EducationRequest request, @MappingTarget @NonNull Education target);
}
