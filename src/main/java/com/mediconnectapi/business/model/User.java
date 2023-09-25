package com.mediconnectapi.business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private UUID uuid;
  private String firstName;
  private String lastName;
  private String email;
  private UserRole role;
}
