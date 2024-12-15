package com.example.travelwise.mapper;

import com.example.travelwise.dto.CartDTO;
import com.example.travelwise.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(source = "user.id", target = "userId")
    CartDTO mapToDTO(Cart cart);

    @Mapping(source = "userId", target = "user.id")
    Cart mapToEntity(CartDTO cartDTO);
}
