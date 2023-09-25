package com.mediconnectapi.application.auth.controller;

import com.mediconnectapi.application.auth.dto.JwtAuthenticationResponse;
import com.mediconnectapi.application.auth.dto.SignInRequest;
import com.mediconnectapi.application.auth.dto.SignUpRequest;
import com.mediconnectapi.application.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/signUp")
  public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody SignUpRequest request) {
    return ResponseEntity.ok(authenticationService.signUp(request));
  }

  @PostMapping("/signIn")
  public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest request) {
    return ResponseEntity.ok(authenticationService.signIn(request));
  }
}
