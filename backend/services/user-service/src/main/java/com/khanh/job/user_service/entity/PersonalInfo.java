package com.khanh.job.user_service.entity;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.jspecify.annotations.Nullable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalInfo {

  private @Nullable String firstName;
  private @Nullable String lastName;

  private @Nullable String headline;

  private @Nullable String email;
  private @Nullable String phone;
  private @Nullable String city;
  private @Nullable String country;

  private @Nullable String profileImage;

  private @Nullable String linkedinUrl;
  private @Nullable String githubUrl;
  private @Nullable String portfolioUrl;
  private @Nullable String websiteUrl;
}
