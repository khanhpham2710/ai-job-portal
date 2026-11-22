package com.khanh.job.common;

import com.khanh.job.dto.response.BaseResponse;

public abstract class BaseController {

  protected <T> BaseResponse<T> createSuccessResponse(T data) {
    return BaseResponse.success(data);
  }
}
