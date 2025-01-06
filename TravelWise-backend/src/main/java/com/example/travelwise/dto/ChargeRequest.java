package com.example.travelwise.dto;

import lombok.Data;

@Data
public class ChargeRequest {
    private Long amount;
    private String name;
    private String currency;
}
