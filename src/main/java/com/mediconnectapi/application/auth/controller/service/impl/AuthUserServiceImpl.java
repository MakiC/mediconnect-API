package com.mediconnectapi.application.auth.controller.service.impl;

import com.mediconnectapi.application.auth.controller.service.AuthUserService;
import com.mediconnectapi.application.auth.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {

  private final AuthUserRepository authUserRepository;

  @Override
  public UserDetailsService userDetailsService() {
    return username -> authUserRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
