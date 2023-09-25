package com.mediconnectapi.application.service;

import com.mediconnectapi.application.repository.UserRepository;
import com.mediconnectapi.business.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public Optional<User> save(User user) {
    return userRepository.save(user);
  }
}
