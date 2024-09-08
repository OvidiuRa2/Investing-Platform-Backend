package com.proiect.licenta.InvestingPlatform.controller;

import com.google.gson.Gson;
import com.proiect.licenta.InvestingPlatform.dto.PaymentInfo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class StripeController {

    @Value("${stripe.key.secret}")
    private  String secretKey;
    private static Gson gson = new Gson();

    @PostMapping("/payment")
    /**
     * Payment with Stripe checkout page
     *
     * @throws StripeException
     */
    public String paymentWithCheckoutPage(@RequestBody List<PaymentInfo> payments) throws StripeException {

        Stripe.apiKey = secretKey;

        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        if (!payments.isEmpty()) {
            for (PaymentInfo payment : payments) {

                SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                        .setQuantity(payment.getQuantity())
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency(payment.getCurrency())
                                        .setUnitAmount(payment.getAmount())
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName(payment.getName())
                                                        .build())
                                        .build())
                        .build();

                lineItems.add(lineItem);
            }

            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(payments.get(0).getSuccessUrl())
                    .setCancelUrl(payments.get(0).getCancelUrl())
                    .addAllLineItem(lineItems)
                    .build();

            Session session = Session.create(params);

            Map<String, String> responseData = new HashMap<>();

            responseData.put("id", session.getId());

            return gson.toJson(responseData);
        }
        return null;
    }
}
