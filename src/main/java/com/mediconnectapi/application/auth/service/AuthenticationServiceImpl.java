package com.mediconnectapi.application.auth.service;

import com.mediconnectapi.application.auth.dto.JwtAuthenticationResponse;
import com.mediconnectapi.application.auth.dto.SignInRequest;
import com.mediconnectapi.application.auth.dto.SignUpRequest;
import com.mediconnectapi.application.auth.entity.AuthUser;
import com.mediconnectapi.application.auth.entity.AuthUserRole;
import com.mediconnectapi.application.auth.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthUserRepository authUserRepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtService jwtService;

  private final AuthenticationManager authenticationManager;

  @Override
  public JwtAuthenticationResponse signUp(SignUpRequest request) {
    final AuthUser authUser = AuthUser.builder()
        .uuid(UUID.randomUUID())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(AuthUserRole.valueOf(request.getRole()))
        .build();
    authUserRepository.save(authUser);

    final String jwt = jwtService.generateToken(authUser);
    return JwtAuthenticationResponse.builder().token(jwt).build();
  }

  @Override
  public JwtAuthenticationResponse signIn(SignInRequest request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    final AuthUser authUser = authUserRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

    final String jwt = jwtService.generateToken(authUser);
    return JwtAuthenticationResponse.builder().token(jwt).build();
  }
}
