package com.example.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

/** Base configuration for all MapStruct mappers. */
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IMapperBaseConfig {}
