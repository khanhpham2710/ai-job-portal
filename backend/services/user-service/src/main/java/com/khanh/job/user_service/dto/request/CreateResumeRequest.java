package com.khanh.job.user_service.dto.request;

import com.khanh.job.domain.enums.ResumeTemplate;
import com.khanh.job.domain.enums.ResumeVisibility;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateResumeRequest {

  @NotBlank(message = "Resume title is required")
  private String title;

  private ResumeTemplate template;

  private ResumeVisibility visibility;

  private Boolean isDefault;
}
