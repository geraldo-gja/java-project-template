package com.template.domain.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenericMapper<ENTITY, REQUEST_DTO, RESPONSE_DTO> {

    RESPONSE_DTO toResponseDto(ENTITY entity);

    ENTITY toEntity(REQUEST_DTO requestDto);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(REQUEST_DTO dto, @MappingTarget ENTITY entity);
}
