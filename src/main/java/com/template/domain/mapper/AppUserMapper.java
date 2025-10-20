package com.template.domain.mapper;

import org.mapstruct.Mapper;

import com.template.domain.dto.AppUserRequestDto;
import com.template.domain.dto.AppUserResponseDto;
import com.template.domain.entity.AppUser;

@Mapper(config = GenericMapper.class)
public interface AppUserMapper extends GenericMapper<AppUser, AppUserRequestDto, AppUserResponseDto> {

}
