package com.template.controller;

import com.template.controller.apidoc.AppUserDoc;
import com.template.domain.dto.AppUserFilterDto;
import com.template.domain.dto.AppUserRequestDto;
import com.template.domain.dto.AppUserResponseDto;
import com.template.service.AppUserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

import java.net.URI;

import static org.springframework.http.ResponseEntity.created;


@RestController
@RequestMapping("v1/app-users")
@AllArgsConstructor
public class AppUserController implements AppUserDoc {

    private AppUserService userService;

   @Override
    public ResponseEntity<AppUserResponseDto> findById(Long id) {
        AppUserResponseDto response = userService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Page<AppUserResponseDto>> findAll(AppUserFilterDto example, Pageable pageable) {
        Page<AppUserResponseDto> response = userService.findAll(example, pageable);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AppUserResponseDto> create(AppUserRequestDto request) {
        AppUserResponseDto response = userService.create(request);
        URI location = URI.create("/users/" + response.id() );
        return created(location).body(response);
    }

    @Override
    public ResponseEntity<AppUserResponseDto> update(Long id, AppUserRequestDto request) {
        AppUserResponseDto response = userService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
