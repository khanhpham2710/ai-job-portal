package com.khanh.job.common;

import com.khanh.job.domain.SortOrder;
import lombok.Data;
import org.jspecify.annotations.Nullable;

@Data
public class BaseQueryModel {

  private Integer page = 1;

  private Integer pageSize = 10;

  private String sort = "";

  private @Nullable SortOrder orderBy;
}
