package com.example.config;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.MapperConfig;

/** Configuration class for all MapStruct mappers. */
@MapperConfig(unmappedTargetPolicy = IGNORE, componentModel = SPRING)
public class MyMapperConfig {}
