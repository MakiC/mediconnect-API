package com.mediconnectapi.application.auth.controller.service;

import com.mediconnectapi.application.auth.dto.JwtAuthenticationResponse;
import com.mediconnectapi.application.auth.dto.SignInRequest;
import com.mediconnectapi.application.auth.dto.SignUpRequest;

public interface AuthenticationService {

  String getPasswordPublicKey();

  JwtAuthenticationResponse signUp(SignUpRequest request);

  JwtAuthenticationResponse signIn(SignInRequest request);
}
