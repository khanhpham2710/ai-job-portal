package com.khanh.job.user_service.service.impl;

import com.khanh.job.dto.dao.CheckDeletedListDAO;
import com.khanh.job.dto.response.user.*;
import com.khanh.job.exception.NotFoundException;
import com.khanh.job.user_service.dto.dao.ResumeDAO;
import com.khanh.job.user_service.dto.request.*;
import com.khanh.job.user_service.entity.*;
import com.khanh.job.user_service.mapper.ResumeMapper;
import com.khanh.job.user_service.repository.*;
import com.khanh.job.user_service.service.*;
import com.khanh.job.utils.CheckDeletedListUtil;
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
  private final WorkExperienceRepository workExperienceRepository;
  private final LanguageRepository languageRepository;
  private final EducationRepository educationRepository;
  private final ProjectRepository projectRepository;
  private final ResumeSkillRepository resumeSkillRepository;

  private final UserQueryService userQueryService;
  private final ResumeQueryService resumeQueryService;
  private final WorkExperienceQueryService workExperienceQueryService;
  private final EducationQueryService educationQueryService;
  private final ResumeSkillQueryService resumeSkillQueryService;
  private final ProjectQueryService projectQueryService;
  private final LanguageQueryService languageQueryService;

  private final ResumeMapper resumeMapper;

  @Override
  @Transactional
  public BaseResumeResponse createResume(CRUDResumeRequest req) {
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
            .summary(req.getSummary())
            .isDefault(Boolean.TRUE.equals(req.getIsDefault()))
            .build();

    List<WorkExperience> workExperiences = resumeMapper.toWorkExperiences(req.getWorkExperiences());

    List<ResumeSkill> resumeSkills = resumeMapper.toSkills(req.getSkills());

    List<Language> languages = resumeMapper.toLanguages(req.getLanguages());

    List<Project> projects = resumeMapper.toProjects(req.getProjects());

    List<Education> educations = resumeMapper.toEducations(req.getEducations());

    Resume saved = resumeRepository.add(resume);

    if (workExperiences != null) {
      workExperiences.forEach(e -> e.setResume(resume));
      workExperienceRepository.addAll(workExperiences);
    }
    if (resumeSkills != null) {
      resumeSkills.forEach(e -> e.setResume(resume));
      resumeSkillRepository.addAll(resumeSkills);
    }
    if (languages != null) {
      languages.forEach(e -> e.setResume(resume));
      languageRepository.addAll(languages);
    }
    if (projects != null) {
      projects.forEach(e -> e.setResume(resume));
      projectRepository.addAll(projects);
    }
    if (educations != null) {
      educations.forEach(e -> e.setResume(resume));
      educationRepository.addAll(educations);
    }

    return buildBaseResponse(saved);
  }

  @Override
  @Transactional
  public BaseResumeResponse updateResume(UUID resumeId, CRUDResumeRequest req) throws Exception {

    UserEntity userEntity = userQueryService.findCurrentUser();
    UUID candidateId = userEntity.getId();

    Resume resume = getResumeEntity(resumeId);
    assertOwner(resume, userEntity);

    boolean isDefault = Boolean.TRUE.equals(req.getIsDefault());

    if (isDefault) {
      resumeQueryService
          .findByCandidateIdAndIsDefault(candidateId, true)
          .filter(existing -> !existing.getId().equals(resumeId))
          .ifPresent(existing -> existing.setIsDefault(false));
    }

    resume.setIsDefault(isDefault);
    resume.setTitle(req.getTitle());
    resume.setTemplate(req.getTemplate());
    resume.setVisibility(req.getVisibility());
    resume.setSummary(req.getSummary());

    ResumeDAO resumeDAO = getResumeDAO(resumeId);

    CheckDeletedListDAO<WorkExperience> workChanges =
        CheckDeletedListUtil.check(
            req.getWorkExperiences(),
            resumeDAO.getWorkExperiences(),
            WorkExperienceRequest::getId,
            WorkExperience::getId,
            r -> {
              WorkExperience e = resumeMapper.toWorkExperience(r);
              e.setResume(resume);
              return e;
            },
            resumeMapper::updateWorkExperience);

    CheckDeletedListDAO<ResumeSkill> skillChanges =
        CheckDeletedListUtil.check(
            req.getSkills(),
            resumeDAO.getResumeSkills(),
            ResumeSkillRequest::getId,
            ResumeSkill::getId,
            r -> {
              ResumeSkill e = resumeMapper.toSkill(r);
              e.setResume(resume);
              return e;
            },
            resumeMapper::updateSkill);

    CheckDeletedListDAO<Language> languageChanges =
        CheckDeletedListUtil.check(
            req.getLanguages(),
            resumeDAO.getLanguages(),
            LanguageRequest::getId,
            Language::getId,
            r -> {
              Language e = resumeMapper.toLanguage(r);
              e.setResume(resume);
              return e;
            },
            resumeMapper::updateLanguage);

    CheckDeletedListDAO<Project> projectChanges =
        CheckDeletedListUtil.check(
            req.getProjects(),
            resumeDAO.getProjects(),
            ProjectRequest::getId,
            Project::getId,
            r -> {
              Project e = resumeMapper.toProject(r);
              e.setResume(resume);
              return e;
            },
            resumeMapper::updateProject);

    CheckDeletedListDAO<Education> educationChanges =
        CheckDeletedListUtil.check(
            req.getEducations(),
            resumeDAO.getEducations(),
            EducationRequest::getId,
            Education::getId,
            r -> {
              Education e = resumeMapper.toEducation(r);
              e.setResume(resume);
              return e;
            },
            resumeMapper::updateEducation);

    if (!workChanges.getNewItems().isEmpty()) {
      workExperienceRepository.addAll(workChanges.getNewItems());
    }
    if (!skillChanges.getNewItems().isEmpty()) {
      resumeSkillRepository.addAll(skillChanges.getNewItems());
    }
    if (!languageChanges.getNewItems().isEmpty()) {
      languageRepository.addAll(languageChanges.getNewItems());
    }
    if (!projectChanges.getNewItems().isEmpty()) {
      projectRepository.addAll(projectChanges.getNewItems());
    }
    if (!educationChanges.getNewItems().isEmpty()) {
      educationRepository.addAll(educationChanges.getNewItems());
    }

    // 5. Delete removed entities
    workExperienceRepository.softDeleteAll(workChanges.getDeletedItems());
    resumeSkillRepository.softDeleteAll(skillChanges.getDeletedItems());
    languageRepository.softDeleteAll(languageChanges.getDeletedItems());
    projectRepository.softDeleteAll(projectChanges.getDeletedItems());
    educationRepository.softDeleteAll(educationChanges.getDeletedItems());

    Resume saved = resumeRepository.update(resume);

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
    Resume updated = resumeRepository.update(resume);

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
    Resume updated = resumeRepository.update(resume);
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

  private ResumeDAO getResumeDAO(UUID resumeId) {
    List<WorkExperience> workExperiences =
        workExperienceQueryService.findByResumeIdOrderByDisplayOrderAsc(resumeId);
    List<Education> educations =
        educationQueryService.findByResumeIdOrderByDisplayOrderAsc(resumeId);
    List<ResumeSkill> skills =
        resumeSkillQueryService.findByResumeIdOrderByDisplayOrderAsc(resumeId);

    List<Project> projects = projectQueryService.findByResumeIdOrderByDisplayOrderAsc(resumeId);

    List<Language> languages = languageQueryService.findByResumeIdOrderByDisplayOrderAsc(resumeId);

    return ResumeDAO.builder()
        .languages(languages)
        .workExperiences(workExperiences)
        .resumeSkills(skills)
        .projects(projects)
        .educations(educations)
        .build();
  }

  private ResumeResponse buildFullResponse(Resume resume) {
    UUID resumeId = resume.getId();

    ResumeDAO resumeDAO = getResumeDAO(resumeId);

    List<WorkExperienceResponse> workExperienceResponses =
        resumeDAO.getWorkExperiences().stream()
            .map(resumeMapper::toWorkExperienceResponse)
            .toList();
    List<EducationResponse> educationResponses =
        resumeDAO.getEducations().stream().map(resumeMapper::toEducationResponse).toList();
    List<ResumeSkillResponse> skills =
        resumeDAO.getResumeSkills().stream().map(resumeMapper::toSkillResponse).toList();

    List<ProjectResponse> projects =
        resumeDAO.getProjects().stream().map(resumeMapper::toProjectResponse).toList();

    List<LanguageResponse> languageResponses =
        resumeDAO.getLanguages().stream().map(resumeMapper::toLanguageResponse).toList();

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
