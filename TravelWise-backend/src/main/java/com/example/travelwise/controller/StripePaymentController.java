package com.example.travelwise.controller;

import com.example.travelwise.dto.ChargeRequest;
import com.example.travelwise.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stripe-payment")
public class StripePaymentController {
    private final StripeService stripeService;

    @Autowired
    public StripePaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<?> createCheckoutSession(@RequestBody ChargeRequest chargeRequest) throws Exception{
        try {
            String sessionId = stripeService.createCharge(chargeRequest);
            Map<String, String> response = new HashMap<>();
            response.put("sessionId", sessionId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating checkout session: " + e.getMessage());
        }
    }
}
