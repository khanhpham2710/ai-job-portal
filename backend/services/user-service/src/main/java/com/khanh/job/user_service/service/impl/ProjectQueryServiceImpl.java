package com.khanh.job.user_service.service.impl;

import com.khanh.job.user_service.entity.Project;
import com.khanh.job.user_service.entity.Project_;
import com.khanh.job.user_service.entity.specifications.ProjectSpecification;
import com.khanh.job.user_service.repository.ProjectRepository;
import com.khanh.job.user_service.service.ProjectQueryService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectQueryServiceImpl implements ProjectQueryService {
  private final ProjectRepository projectRepository;

  @Override
  public List<Project> findByResumeIdOrderByDisplayOrderAsc(UUID resumeId) {
    return projectRepository.findAllActive(
        ProjectSpecification.byResumeId(resumeId),
        Sort.by(Sort.Direction.ASC, Project_.DISPLAY_ORDER));
  }
}
