package com.mediconnectapi.application.auth.service;

import com.mediconnectapi.application.auth.dto.JwtAuthenticationResponse;
import com.mediconnectapi.application.auth.dto.SignInRequest;
import com.mediconnectapi.application.auth.dto.SignUpRequest;

public interface AuthenticationService {

  JwtAuthenticationResponse signUp(SignUpRequest request);

  JwtAuthenticationResponse signIn(SignInRequest request);
}
