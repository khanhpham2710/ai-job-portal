package com.khanh.job.user_service.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalInfoRequest {
  private String firstName;
  private String lastName;

  private String headline;

  private String email;
  private String phone;
  private String city;
  private String country;

  private String profileImage;

  private String linkedinUrl;
  private String githubUrl;
  private String portfolioUrl;
  private String websiteUrl;
}
