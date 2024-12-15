package com.example.travelwise.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartDTO {
    private Long id;
    @NotNull(message = "total price cannot be null")
    private Double totalPrice;
    @NotNull(message = "Number of items cannot be null")
    private Integer numberOfItems;
    @NotNull(message = "user id cannot be null")
    private Long userId;
}
