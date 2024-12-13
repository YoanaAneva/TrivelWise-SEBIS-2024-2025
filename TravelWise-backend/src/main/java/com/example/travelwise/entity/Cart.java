package com.example.travelwise.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double totalPrice;
    private boolean isEmpty;
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
}
