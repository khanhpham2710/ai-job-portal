package com.khanh.job.common;

import com.khanh.job.dto.response.BaseResponse;
import org.jspecify.annotations.Nullable;

public abstract class BaseController {

  protected <T> BaseResponse<T> createSuccessResponse(@Nullable T data) {
    return BaseResponse.success(data);
  }
}
