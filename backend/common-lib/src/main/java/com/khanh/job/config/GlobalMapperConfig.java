package com.khanh.job.config;

import com.khanh.job.mapper.GlobalDateTimeMapper;
import com.khanh.job.mapper.GlobalEmbeddableMapper;
import org.mapstruct.*;

@MapperConfig(
    componentModel = "spring",
    uses = {GlobalDateTimeMapper.class, GlobalEmbeddableMapper.class},
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    builder = @Builder(disableBuilder = true))
public interface GlobalMapperConfig {}
