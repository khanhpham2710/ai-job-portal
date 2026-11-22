package com.khanh.job.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Set;
import org.jspecify.annotations.Nullable;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public record ErrorResponse<T>(Date timestamp, String path, T message) {

  public static ErrorResponse<String> error(String message) {
    HttpServletRequest request = getCurrentHttpRequest();

    String path = request != null ? request.getRequestURI() : "";

    return new ErrorResponse<>(new Date(), path, message);
  }

  public static ErrorResponse<Set<String>> error(Set<String> message) {
    HttpServletRequest request = getCurrentHttpRequest();

    String path = request != null ? request.getRequestURI() : "";

    return new ErrorResponse<>(new Date(), path, message);
  }

  private static @Nullable HttpServletRequest getCurrentHttpRequest() {
    RequestAttributes attributes = RequestContextHolder.getRequestAttributes();

    if (attributes instanceof ServletRequestAttributes servletAttributes) {
      return servletAttributes.getRequest();
    }

    return null;
  }
}
