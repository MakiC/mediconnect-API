package com.mediconnectapi.application.repository.mapper;

import com.mediconnectapi.application.repository.entity.UserEntity;
import com.mediconnectapi.business.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper extends EntityMapper<User, UserEntity> {

}
