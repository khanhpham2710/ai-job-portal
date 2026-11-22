package com.khanh.job.user_service;

import com.khanh.job.config.ApplicationConfiguration;
import com.khanh.job.config.AuditingConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ApplicationConfiguration.class, AuditingConfiguration.class})
public class UserServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }
}
