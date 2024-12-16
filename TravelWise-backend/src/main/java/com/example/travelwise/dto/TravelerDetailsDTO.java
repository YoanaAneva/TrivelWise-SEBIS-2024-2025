package com.example.travelwise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TravelerDetailsDTO {
    private Long id;
    @NotBlank(message = "traveler first name cannot be blank")
    private String travelerFirstName;
    @NotBlank(message = "traveler surname cannot be blank")
    private String travelerSurname;
    @NotBlank(message = "traveler email cannot be blank")
    private String travelerEmail;
    @NotBlank(message = "traveler phone number cannot be blank")
    private String travelerPhoneNumber;
    @NotNull(message = "reservation id cannot be null")
    private Long reservationId;
}
