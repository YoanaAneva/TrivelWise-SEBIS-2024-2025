package com.example.travelwise.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reservationNum;
    private String clientFirstName;
    private String clientSurname;
    private String clientEmail;
    private String clientPhoneNumber;
    @ManyToOne
    @JoinColumn(name="offer_id")
    private Offer offer;
//    @ManyToOne
//    @JoinColumn(name="cart_id")
//    private Cart cart;
//    @ManyToOne
//    @JoinColumn(name="order_id")
//    private Order order;
}
