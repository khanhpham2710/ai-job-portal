package com.khanh.job.config;

import com.khanh.job.common.CommonAppProperties;
import com.khanh.job.common.QueryModelResolver;
import com.khanh.job.exception.GlobalExceptionHandler;
import java.util.List;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration
public class ApplicationConfiguration implements WebMvcConfigurer {
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new QueryModelResolver());
  }

  @Bean
  @ConditionalOnMissingBean(CommonAppProperties.class)
  CommonAppProperties commonAppProperties() {
    return new CommonAppProperties();
  }

  @Bean
  @ConditionalOnMissingBean(SecurityFilterChain.class)
  SecurityFilterChain securityFilterChain(HttpSecurity http, CommonAppProperties props)
      throws Exception {
    String[] publicPaths = props.getSecurity().getJwt().getPublicPaths().toArray(String[]::new);

    http.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth -> auth.requestMatchers(publicPaths).permitAll().anyRequest().authenticated())
        .exceptionHandling(
            e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

    return http.build();
  }

  @Bean
  @ConditionalOnMissingBean(GlobalExceptionHandler.class)
  GlobalExceptionHandler globalExceptionHandler() {
    return new GlobalExceptionHandler();
  }
}
