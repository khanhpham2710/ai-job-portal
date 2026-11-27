package com.khanh.job.user_service.mapper;

import com.khanh.job.config.GlobalMapperConfig;
import com.khanh.job.user_service.dto.response.UserResponse;
import com.khanh.job.user_service.entity.UserEntity;
import org.jspecify.annotations.NonNull;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public interface UserMapper {
  UserResponse toResponse(@NonNull UserEntity user);
}
