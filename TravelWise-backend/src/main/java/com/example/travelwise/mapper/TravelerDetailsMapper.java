package com.example.travelwise.mapper;

import com.example.travelwise.dto.TravelerDetailsDTO;
import com.example.travelwise.entity.TravelerDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TravelerDetailsMapper {
    @Mapping(source = "reservation.id", target = "reservationId")
    TravelerDetailsDTO mapToDTO(TravelerDetails userEntity);

    @Mapping(source = "reservationId", target = "reservation.id")
    TravelerDetails mapToEntity(TravelerDetailsDTO userDTO);
}
