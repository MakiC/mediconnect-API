package com.mediconnectapi.application.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthUserService {

  UserDetailsService userDetailsService();
}
