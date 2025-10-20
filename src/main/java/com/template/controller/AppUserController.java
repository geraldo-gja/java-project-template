package com.template.controller;

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
public class AppUserController {

    private AppUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<AppUserResponseDto> findById(@PathVariable Long id) {
        AppUserResponseDto response = userService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<AppUserResponseDto>> findAll(
            AppUserRequestDto example,
            Pageable pageable) {
        Page<AppUserResponseDto> response = userService.findAll(example, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AppUserResponseDto> create(@RequestBody AppUserRequestDto request) {

        AppUserResponseDto response = userService.create(request);
        URI location = URI.create("/users/" + response.id() );
        return created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUserResponseDto> update(
            @PathVariable Long id,
            @RequestBody AppUserRequestDto request) {

        AppUserResponseDto response = userService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
