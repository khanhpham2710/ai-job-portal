package com.khanh.job.dto.response.user;

import com.khanh.job.domain.enums.LanguageProficiency;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageResponse {

  private UUID id;
  private String languageName;
  private LanguageProficiency proficiency;
  private Integer displayOrder;
}
