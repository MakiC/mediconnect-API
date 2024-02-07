package com.mediconnectapi.application.auth.controller.service.impl;

import com.mediconnectapi.application.auth.controller.service.AuthenticationService;
import com.mediconnectapi.application.auth.controller.service.JwtService;
import com.mediconnectapi.application.auth.dto.JwtAuthenticationResponse;
import com.mediconnectapi.application.auth.dto.SignInRequest;
import com.mediconnectapi.application.auth.dto.SignUpRequest;
import com.mediconnectapi.application.auth.entity.AuthUser;
import com.mediconnectapi.application.auth.entity.AuthUserRole;
import com.mediconnectapi.application.auth.repository.AuthUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthUserRepository authUserRepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtService jwtService;

  private final AuthenticationManager authenticationManager;

  private PublicKey passwordPublicKey;

  private PrivateKey passwordPrivateKey;

  @PostConstruct
  void init() {
    initPasswordKeys();
    testPasswordKeys();
  }

  @Override
  public String getPasswordPublicKey() {
    return Base64.getEncoder().encodeToString(passwordPublicKey.getEncoded());
  }

  @Override
  public JwtAuthenticationResponse signUp(SignUpRequest request) {
    final AuthUser authUser = AuthUser.builder()
        .uid(UUID.randomUUID())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(decryptPassword(request.getPassword())))
        .role(AuthUserRole.valueOf(request.getRole()))
        .build();

    try {
      authUserRepository.save(authUser);
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityViolationException("Email already exists");
    }

    final String jwt = jwtService.generateToken(authUser);
    return JwtAuthenticationResponse.builder().token(jwt).build();
  }

  @Override
  public JwtAuthenticationResponse signIn(SignInRequest request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), decryptPassword(request.getPassword())));

    final AuthUser authUser = authUserRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

    final String jwt = jwtService.generateToken(authUser);
    return JwtAuthenticationResponse.builder().token(jwt).build();
  }

  @SneakyThrows
  private String encryptPassword(String password) {
    final Cipher encryptCipher = Cipher.getInstance("RSA");
    encryptCipher.init(Cipher.ENCRYPT_MODE, passwordPublicKey);

    return Base64.getEncoder().encodeToString(encryptCipher.doFinal(password.getBytes(StandardCharsets.UTF_8)));
  }

  @SneakyThrows
  private String decryptPassword(String password) {
    final Cipher decryptCipher = Cipher.getInstance("RSA");
    decryptCipher.init(Cipher.DECRYPT_MODE, passwordPrivateKey);

    return new String(decryptCipher.doFinal(Base64.getDecoder().decode(password)), StandardCharsets.UTF_8);
  }

  @SneakyThrows
  private void initPasswordKeys() {
    final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(2048);

    final KeyPair keyPair = keyPairGenerator.generateKeyPair();
    passwordPublicKey = keyPair.getPublic();
    passwordPrivateKey = keyPair.getPrivate();
  }

  private void testPasswordKeys() {
    final String password = "Hello World! This is a password : <ééé|èèè>@#*^%$£!";

    final String encryptedPassword = encryptPassword(password);
    final String decryptedPassword = decryptPassword(encryptedPassword);

    if (!decryptedPassword.equals(password)) {
      throw new RuntimeException("RSA keys are not working!");
    }
  }
}
