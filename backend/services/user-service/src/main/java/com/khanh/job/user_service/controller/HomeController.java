package com.khanh.job.user_service.controller;

import com.khanh.job.common.BaseController;
import com.khanh.job.common.BaseQueryModel;
import com.khanh.job.common.QueryModel;
import com.khanh.job.domain.SortOrder;
import com.khanh.job.dto.response.BaseResponse;
import com.khanh.job.dto.response.PaginationResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController extends BaseController {

  @GetMapping
  public BaseResponse<String> Home() {
    return createSuccessResponse("Hello World");
  }

  @GetMapping("/list")
  public BaseResponse<List<String>> HomeList() {
    return createSuccessResponse(
        new ArrayList<>() {
          {
            add("Hello World 1");
            add("Hello World 2");
          }
        });
  }

  @GetMapping("/pagination")
  public PaginationResponse<String> HomePagination(@QueryModel BaseQueryModel queryModel) {
    List<String> list =
        new ArrayList<>() {
          {
            add("Hello World 1");
            add("Hello World 2");
            add("Hello World 3");
          }
        };

    List<String> sorted = list.stream().sorted(String::compareTo).toList();
    if (queryModel.getOrderBy() == SortOrder.ASC) {
      list = sorted;
    } else {
      list = sorted.reversed();
    }

    return PaginationResponse.pagination(
        list, queryModel.getPage(), queryModel.getPageSize(), list.size());
  }
}
