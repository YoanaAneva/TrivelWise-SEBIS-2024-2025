package com.example.travelwise.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TravelerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String travelerFirstName;
    private String travelerSurname;
    private String travelerEmail;
    private String travelerPhoneNumber;
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}
