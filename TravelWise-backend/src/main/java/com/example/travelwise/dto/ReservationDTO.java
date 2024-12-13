package com.example.travelwise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationDTO {
    private Long id;
    @NotBlank(message = "reservation number cannot be blank")
    private String reservationNum;
    @NotBlank(message = "client first name cannot be blank")
    private String clientFirstName;
    @NotBlank(message = "client surname cannot be blank")
    private String clientSurname;
    @NotBlank(message = "client email cannot be blank")
    private String clientEmail;
    @NotBlank(message = "client phone number cannot be blank")
    private String clientPhoneNumber;
    @NotNull(message = "offer id cannot be null")
    private Long offerId;
    @NotNull(message = "cart id cannot be null")
    private Long cartId;
//    @NotNull(message = "order id cannot be null")
//    private Long orderId;
    @NotBlank(message = "offer title cannot be blank")
    private String offerTitle;
    private String offerImageUrl;
}
