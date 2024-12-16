package com.example.travelwise.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reservationNum;
    private Double totalPrice;
    private LocalDate reservationDate;
    private boolean isPaid;
    @ManyToOne
    @JoinColumn(name="offer_id")
    private Offer offer;
    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelerDetails> travelers = new ArrayList<>();

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", reservationNum='" + reservationNum + '\'' +
                ", totalPrice=" + totalPrice +
                ", reservationDate=" + reservationDate +
                ", isPaid=" + isPaid +
                ", offerId=" + (offer != null ? offer.getId() : null) +
                ", cartId=" + (cart != null ? cart.getId() : null) +
                ", travelersCount=" + (travelers != null ? travelers.size() : 0) +
                '}';
    }
}
