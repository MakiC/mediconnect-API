package com.mediconnectapi.application.auth.controller.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthUserService {

  UserDetailsService userDetailsService();
}
