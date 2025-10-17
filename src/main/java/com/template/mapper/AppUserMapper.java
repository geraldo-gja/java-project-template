package com.template.mapper;

import org.mapstruct.Mapper;

import com.template.dto.AppUserRequestDto;
import com.template.dto.AppUserResponseDto;
import com.template.entity.AppUser;

@Mapper(config = GenericMapper.class)
public interface AppUserMapper extends GenericMapper<AppUser, AppUserRequestDto, AppUserResponseDto> {

}
