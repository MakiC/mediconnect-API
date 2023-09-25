package com.mediconnectapi.application.controller.mapper;

import com.mediconnectapi.application.controller.dto.UserDto;
import com.mediconnectapi.business.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper extends DtoMapper<User, UserDto> {

}
