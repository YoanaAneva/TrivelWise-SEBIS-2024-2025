package com.example.travelwise.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OfferFiltersDTO {
    @NotNull(message = "Offer category id cannot be null")
    private Long categoryId;
    @Positive
    private Double minPrice;
    @Positive
    private Double maxPrice;
    private LocalDate startsAfter;
    private LocalDate endsBefore;
    @Positive
    private Integer minAvailableSpots;
}