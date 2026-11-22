package com.khanh.job.dto.response;

import org.jspecify.annotations.Nullable;

public record BaseResponse<T>(boolean isSuccess, @Nullable T result) {

  public static <T> BaseResponse<T> success(@Nullable T result) {
    return new BaseResponse<>(true, result);
  }

  public static <T> BaseResponse<T> error(T result) {
    return new BaseResponse<>(false, result);
  }
}
