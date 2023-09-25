package com.mediconnectapi.application.controller;

import com.mediconnectapi.application.controller.dto.UserDto;
import com.mediconnectapi.application.controller.mapper.UserDtoMapper;
import com.mediconnectapi.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  private final UserDtoMapper userDtoMapper;

  @GetMapping
  public ResponseEntity<List<UserDto>> findAll() {
    return ResponseEntity.ok(userService.findAll().stream()
        .map(userDtoMapper::toDto)
        .toList());
  }
}
