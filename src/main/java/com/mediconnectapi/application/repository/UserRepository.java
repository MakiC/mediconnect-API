package com.mediconnectapi.application.repository;

import com.mediconnectapi.application.repository.jpa.UserJpaRepository;
import com.mediconnectapi.application.repository.mapper.UserEntityMapper;
import com.mediconnectapi.business.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepository {

  private final UserJpaRepository userJpaRepository;

  private final UserEntityMapper userEntityMapper;

  public List<User> findAll() {
    return userJpaRepository.findAll().stream()
        .map(userEntityMapper::toModel)
        .toList();
  }

  public Optional<User> save(User user) {
    return Optional.ofNullable(user)
        .map(userEntityMapper::toEntity)
        .map(userJpaRepository::save)
        .map(userEntityMapper::toModel);
  }
}
