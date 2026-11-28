package com.khanh.job.dto.dao;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CheckDeletedListDAO<T> {
  private List<T> newItems;
  private List<T> updateItems;
  private List<T> deletedItems;
}
