package com.template.service;

import com.template.dto.AppUserRequestDto;

import com.template.dto.AppUserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppUserService {

    AppUserResponseDto create(AppUserRequestDto requestDto);

    AppUserResponseDto findById(Long id);

    Page<AppUserResponseDto> findAll(AppUserRequestDto exampleDto, Pageable pageable);

    AppUserResponseDto update(Long id, AppUserRequestDto requestDto);

    void delete(Long id);
}
