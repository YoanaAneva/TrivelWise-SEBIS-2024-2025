package com.example.travelwise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReservationDTO {
    private Long id;
    @NotBlank(message = "reservation number cannot be blank")
    private String reservationNum;
    @NotNull(message = "total price cannot be null")
    private Double totalPrice;
    @NotNull(message = "reservation date cannot be null")
    private LocalDate reservationDate;
    private boolean isPaid;
    @NotNull(message = "offer id cannot be null")
    private Long offerId;
    @NotNull(message = "cart id cannot be null")
    private Long cartId;
    @NotBlank(message = "offer title cannot be blank")
    private List<TravelerDetailsDTO> travelers;

    private String offerTitle;
    private String offerImageUrl;
}
