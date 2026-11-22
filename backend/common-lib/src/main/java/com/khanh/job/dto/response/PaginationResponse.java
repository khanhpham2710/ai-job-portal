package com.khanh.job.dto.response;

import java.util.List;

public record PaginationResponse<T>(
    List<T> data, int page, int pageSize, int totalPage, int count) {
  public static <T> PaginationResponse<T> pagination(
      List<T> data, int page, int pageSize, int count) {
    int totalPage = count / pageSize;

    return new PaginationResponse<>(data, page, pageSize, totalPage, count);
  }
}
