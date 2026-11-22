package com.khanh.job.config;

import com.khanh.job.common.AppUserPrincipal;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@AutoConfiguration
@ConditionalOnClass({EntityManager.class, EnableJpaAuditing.class})
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditingConfiguration {

  @Bean(name = "auditorAware")
  @ConditionalOnMissingBean(name = "auditorAware")
  public AuditorAware<String> auditorAware() {
    return () -> {
      var authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication != null
          && authentication.isAuthenticated()
          && authentication.getPrincipal() instanceof AppUserPrincipal principal) {
        return Optional.of(principal.getName());
      }

      var requestAttributes = RequestContextHolder.getRequestAttributes();

      if (requestAttributes instanceof ServletRequestAttributes attributes) {
        Object userId = attributes.getRequest().getAttribute("userId");

        if (userId instanceof String id) {
          return Optional.of(id);
        }
      }

      return Optional.of("system");
    };
  }
}
