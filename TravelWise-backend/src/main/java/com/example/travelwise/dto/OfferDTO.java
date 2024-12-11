package com.example.travelwise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OfferDTO {
    private Long id;
    @NotBlank(message = "Offer title cannot be blank")
    private String title;
    @NotBlank(message = "Offer description cannot be blank")
    private String description;
    @NotBlank(message = "Offer location cannot be blank")
    private String location;
    @NotNull(message = "Offer price cannot be null")
    private Double price;
    @NotNull(message = "Offer start date cannot be null")
    private LocalDate startDate;
    @NotNull(message = "Offer end cannot be null")
    private LocalDate endDate;
    @NotNull(message = "Offer available spots cannot be null")
    private Integer availableSpots;
    @NotNull(message = "Offer category cannot be null")
    private Long categoryId;
}
