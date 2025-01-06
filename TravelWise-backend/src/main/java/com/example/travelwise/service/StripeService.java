package com.example.travelwise.service;

import com.example.travelwise.dto.ChargeRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;


@Service
public class StripeService {
    private String stripeSecretKey = System.getenv("ENV_VAR_STRIPE_SECRET_KEY");

    public StripeService() {
        Stripe.apiKey = stripeSecretKey;
    }

    public String createCharge(ChargeRequest chargeRequest) throws StripeException {
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(chargeRequest.getName())
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(chargeRequest.getCurrency() != null ? chargeRequest.getCurrency() : "USD")
                        .setUnitAmount(chargeRequest.getAmount())
                        .setProductData(productData)
                        .build();

        SessionCreateParams.LineItem lineItem =
                SessionCreateParams
                        .LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(priceData)
                        .build();

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:4200/success")
                        .setCancelUrl("http://localhost:4200/cancel")
                        .addLineItem(lineItem)
                        .build();

        Session session = Session.create(params);
        return session.getId();
    }
}
