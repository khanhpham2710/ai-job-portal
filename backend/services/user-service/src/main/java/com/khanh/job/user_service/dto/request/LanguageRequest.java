package com.khanh.job.user_service.dto.request;

import com.khanh.job.domain.enums.LanguageProficiency;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageRequest {

  private UUID id;
  private String languageName;
  private LanguageProficiency proficiency;
  private Integer displayOrder;
}
