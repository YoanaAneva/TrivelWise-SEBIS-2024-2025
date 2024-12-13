package com.example.travelwise.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartDTO {
    private Long id;
    @NotNull(message = "total price cannot be null")
    private Double totalPrice;
    private boolean isEmpty;
    @NotNull(message = "user id cannot be null")
    private Long userId;
}
