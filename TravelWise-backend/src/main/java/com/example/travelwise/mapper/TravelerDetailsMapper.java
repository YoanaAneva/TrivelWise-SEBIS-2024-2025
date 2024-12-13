package com.example.travelwise.mapper;

import com.example.travelwise.dto.UserDTO;
import com.example.travelwise.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TravelerDetailsMapper {
    @Mapping(source = "reservation.id", target = "reservationId")
    UserDTO mapToDTO(User userEntity);

    @Mapping(source = "reservationId", target = "reservation.id")
    User mapToEntity(UserDTO userDTO);
}
