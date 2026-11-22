package com.khanh.job.common;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class QueryModelResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(QueryModel.class);
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      @Nullable ModelAndViewContainer mavContainer,
      @NonNull NativeWebRequest webRequest,
      @Nullable WebDataBinderFactory binderFactory)
      throws Exception {
    Class<?> parameterType = parameter.getParameterType();

    Object queryModel = BeanUtils.instantiateClass(parameterType);

    WebDataBinder binder =
        Objects.requireNonNull(binderFactory)
            .createBinder(
                webRequest, queryModel, Objects.requireNonNull(parameter.getParameterName()));

    binder.bind(
        new org.springframework.web.bind.ServletRequestParameterPropertyValues(
            Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class))));

    return queryModel;
  }
}
