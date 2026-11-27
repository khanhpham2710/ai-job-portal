package com.khanh.job.user_service;

import com.khanh.job.config.ApplicationConfiguration;
import com.khanh.job.config.AuditingConfiguration;
import com.khanh.job.utils.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.khanh.job")
@Import({ApplicationConfiguration.class, AuditingConfiguration.class})
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class UserServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }
}
