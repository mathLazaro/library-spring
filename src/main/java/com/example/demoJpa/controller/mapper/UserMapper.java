package com.example.demoJpa.controller.mapper;

import com.example.demoJpa.controller.dto.users.UserDTO;
import com.example.demoJpa.controller.dto.users.UserOutputDTO;
import com.example.demoJpa.domain.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Users toUser(UserDTO usersDTO);
    UserOutputDTO toUserOutputDTO(Users user);
}
