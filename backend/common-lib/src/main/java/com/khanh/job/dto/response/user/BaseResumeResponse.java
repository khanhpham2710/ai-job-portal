package com.khanh.job.dto.response.user;

import com.khanh.job.domain.enums.ResumeTemplate;
import com.khanh.job.domain.enums.ResumeVisibility;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import org.jspecify.annotations.Nullable;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class BaseResumeResponse {
  private @Nullable UUID id;
  private @Nullable UUID candidateId;
  private @Nullable String title;
  private @Nullable ResumeTemplate template;
  private @Nullable ResumeVisibility visibility;
  private @Nullable Boolean isDefault;
  private @Nullable PersonalInfoResponse personalInfo;
  private @Nullable String summary;
  private @Nullable Integer completionScore;
  private @Nullable LocalDateTime createdAt;
  private @Nullable LocalDateTime updatedAt;
}
