package com.khanh.job.utils;

import com.khanh.job.common.AppUserPrincipal;
import java.util.Objects;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUser {
  public static @Nullable AppUserPrincipal getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (Objects.isNull(authentication)) return null;
    return (AppUserPrincipal) authentication.getPrincipal();
  }
}
