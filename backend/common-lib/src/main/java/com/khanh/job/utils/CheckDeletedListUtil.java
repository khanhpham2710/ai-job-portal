package com.khanh.job.utils;

import com.khanh.job.dto.dao.CheckDeletedListDAO;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CheckDeletedListUtil {

  private CheckDeletedListUtil() {}

  public static <S, T> CheckDeletedListDAO<T> check(
      List<S> requestList,
      List<T> existingList,
      Function<S, UUID> requestIdGetter,
      Function<T, UUID> existingIdGetter,
      Function<S, T> creator, // tạo mới: toEntity(request)
      BiConsumer<S, T> updater // cập nhật: update(request, entity)
      ) {
    Map<UUID, T> existingMap =
        existingList.stream()
            .filter(e -> existingIdGetter.apply(e) != null)
            .collect(Collectors.toMap(existingIdGetter, Function.identity(), (a, b) -> a));

    Set<UUID> requestIds =
        requestList.stream()
            .map(requestIdGetter)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

    List<T> newItems = new ArrayList<>();
    List<T> updateItems = new ArrayList<>();

    for (S request : requestList) {
      UUID id = requestIdGetter.apply(request);
      T existing = id == null ? null : existingMap.get(id);

      if (existing == null) {
        T newItem = creator.apply(request);
        newItems.add(newItem);
      } else {
        updater.accept(request, existing); // ghi đè field từ request vào entity cũ
        updateItems.add(existing);
      }
    }

    List<T> deletedItems =
        existingList.stream()
            .filter(
                e -> {
                  UUID id = existingIdGetter.apply(e);
                  return id != null && !requestIds.contains(id);
                })
            .toList();

    return CheckDeletedListDAO.<T>builder()
        .newItems(newItems)
        .updateItems(updateItems)
        .deletedItems(deletedItems)
        .build();
  }
}
