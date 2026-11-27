package com.khanh.job.user_service.mapper;

import com.khanh.job.config.GlobalMapperConfig;
import com.khanh.job.dto.response.user.*;
import com.khanh.job.user_service.dto.request.PersonalInfoRequest;
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
  @Mapping(target = "displayOrder", source = "displayOrder.displayOrder")
  EducationResponse toEducationResponse(Education edu);

  @Mapping(target = "startDate", source = "rangeInfo.startDate")
  @Mapping(target = "endDate", source = "rangeInfo.endDate")
  @Mapping(target = "displayOrder", source = "displayOrder.displayOrder")
  ProjectResponse toProjectResponse(Project project);

  LanguageResponse toLanguageResponse(Language lang);

  @Mapping(target = "startDate", source = "rangeInfo.startDate")
  @Mapping(target = "endDate", source = "rangeInfo.endDate")
  @Mapping(target = "displayOrder", source = "displayOrder.displayOrder")
  WorkExperienceResponse toWorkExperienceResponse(WorkExperience exp);
}
