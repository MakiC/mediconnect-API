package com.mediconnectapi.application.auth.controller;

import com.mediconnectapi.application.auth.dto.JwtAuthenticationResponse;
import com.mediconnectapi.application.auth.dto.SignInRequest;
import com.mediconnectapi.application.auth.dto.SignUpRequest;
import com.mediconnectapi.application.auth.controller.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @GetMapping("/passwordPublicKey")
  public ResponseEntity<String> getPasswordPublicKey() {
    return ResponseEntity.ok(authenticationService.getPasswordPublicKey());
  }

  @PostMapping("/signUp")
  public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody SignUpRequest request) {
    return ResponseEntity.ok(authenticationService.signUp(request));
  }

  @PostMapping("/signIn")
  public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest request) {
    return ResponseEntity.ok(authenticationService.signIn(request));
  }
}
