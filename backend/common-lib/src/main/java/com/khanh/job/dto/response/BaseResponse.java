package com.khanh.job.dto.response;

public record BaseResponse<T>(boolean isSuccess, T result) {

  public static <T> BaseResponse<T> success(T result) {
    return new BaseResponse<>(true, result);
  }

  public static <T> BaseResponse<T> error(T result) {
    return new BaseResponse<>(false, result);
  }
}
