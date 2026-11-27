package com.khanh.job.user_service.service.impl;

import com.khanh.job.dto.response.user.*;
import com.khanh.job.exception.NotFoundException;
import com.khanh.job.user_service.dto.request.CreateResumeRequest;
import com.khanh.job.user_service.dto.request.PersonalInfoRequest;
import com.khanh.job.user_service.entity.PersonalInfo;
import com.khanh.job.user_service.entity.Resume;
import com.khanh.job.user_service.entity.UserEntity;
import com.khanh.job.user_service.mapper.ResumeMapper;
import com.khanh.job.user_service.repository.ResumeRepository;
import com.khanh.job.user_service.service.*;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeServiceImpl implements ResumeService {
  private final ResumeRepository resumeRepository;

  private final UserQueryService userQueryService;
  private final ResumeQueryService resumeQueryService;
  private final WorkExperienceQueryService workExperienceQueryService;
  private final EducationQueryService educationQueryService;
  private final ResumeSkillQueryService resumeSkillQueryService;
  private final ProjectQueryService projectQueryService;
  private final LanguageQueryService languageQueryService;

  private final ResumeMapper resumeMapper;

  @Override
  public BaseResumeResponse createResume(CreateResumeRequest req) {
    UserEntity userEntity = userQueryService.findCurrentUser();

    UUID candidateId = userEntity.getId();

    if (Boolean.TRUE.equals(req.getIsDefault())) {
      resumeQueryService
          .findByCandidateIdAndIsDefault(candidateId, true)
          .ifPresent(
              existing -> {
                existing.setIsDefault(false);
              });
    }

    Resume resume =
        Resume.builder()
            .candidate(userEntity)
            .title(req.getTitle())
            .template(req.getTemplate())
            .visibility(req.getVisibility())
            .isDefault(Boolean.TRUE.equals(req.getIsDefault()))
            .build();
    Resume saved = resumeRepository.save(resume);
    return buildBaseResponse(saved);
  }

  @Override
  public ResumeResponse getResumeById(UUID resumeId) throws NotFoundException {
    Resume resume = getResumeEntity(resumeId);
    return buildFullResponse(resume);
  }

  @Override
  public List<BaseResumeResponse> getMyResumes() {
    UserEntity userEntity = userQueryService.findCurrentUser();

    return resumeQueryService.findByCandidateId(userEntity.getId()).stream()
        .map(this::buildBaseResponse)
        .toList();
  }

  @Override
  public BaseResumeResponse updatePersonalInfo(UUID resumeId, PersonalInfoRequest req)
      throws Exception {

    Resume resume = getResumeEntity(resumeId);

    assertOwner(resume);

    PersonalInfo info = resume.getPersonalInfo();

    resume.setPersonalInfo(resumeMapper.merge(req, info));
    Resume updated = resumeRepository.save(resume);

    return buildBaseResponse(updated);
  }

  @Override
  public BaseResumeResponse setDefaultResume(UUID resumeId) throws Exception {
    Resume resume = getResumeEntity(resumeId);
    UserEntity userEntity = userQueryService.findCurrentUser();
    assertOwner(resume, userEntity);

    resumeQueryService
        .findByCandidateIdAndIsDefault(userEntity.getId(), true)
        .ifPresent(
            existing -> {
              existing.setIsDefault(false);
            });

    resume.setIsDefault(true);
    Resume updated = resumeRepository.save(resume);
    return buildBaseResponse(updated);
  }

  @Override
  public void deleteResume(UUID resumeId) throws Exception {
    Resume resume = getResumeEntity(resumeId);
    assertOwner(resume);
    resume.setIsDefault(false);
    resumeRepository.softDelete(resume);
  }

  private BaseResumeResponse buildBaseResponse(Resume resume) {
    return resumeMapper.toBaseResponse(resume);
  }

  private ResumeResponse buildFullResponse(Resume resume) {
    UUID resumeId = resume.getId();

    List<WorkExperienceResponse> workExperienceResponses =
        workExperienceQueryService.findByResumeIdOrderByDisplayOrderAsc(resumeId).stream()
            .map(resumeMapper::toWorkExperienceResponse)
            .toList();
    List<EducationResponse> educationResponses =
        educationQueryService.findByResumeIdOrderByDisplayOrderAsc(resumeId).stream()
            .map(resumeMapper::toEducationResponse)
            .toList();
    List<ResumeSkillResponse> skills =
        resumeSkillQueryService.findByResumeIdOrderByDisplayOrderAsc(resumeId).stream()
            .map(resumeMapper::toSkillResponse)
            .toList();

    List<ProjectResponse> projects =
        projectQueryService.findByResumeIdOrderByDisplayOrderAsc(resumeId).stream()
            .map(resumeMapper::toProjectResponse)
            .toList();

    List<LanguageResponse> languageResponses =
        languageQueryService.findByResumeIdOrderByDisplayOrderAsc(resumeId).stream()
            .map(resumeMapper::toLanguageResponse)
            .toList();

    return resumeMapper.toResponse(
        resume, workExperienceResponses, educationResponses, skills, projects, languageResponses);
  }

  private void assertOwner(Resume resume) throws Exception {
    UserEntity userEntity = userQueryService.findCurrentUser();

    UUID candidateId = userEntity.getId();

    if (!resume.getCandidate().getId().equals(candidateId)) {
      throw new Exception("resume candidate id not match");
    }
    ;
  }

  private void assertOwner(Resume resume, UserEntity userEntity) throws Exception {
    UUID candidateId = userEntity.getId();

    if (!resume.getCandidate().getId().equals(candidateId)) {
      throw new Exception("resume candidate id not match");
    }
    ;
  }

  private Resume getResumeEntity(UUID resumeId) throws NotFoundException {
    return resumeRepository
        .findOneActive(resumeId)
        .orElseThrow(() -> new NotFoundException("resume not found with id " + resumeId));
  }
}
