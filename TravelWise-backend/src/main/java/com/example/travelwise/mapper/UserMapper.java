package com.example.travelwise.mapper;


import com.example.travelwise.dto.UserDTO;
import com.example.travelwise.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO mapToDTO(User userEntity);
    User mapToEntity(UserDTO userDTO);
}
