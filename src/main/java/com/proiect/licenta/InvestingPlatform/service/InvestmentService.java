package com.proiect.licenta.InvestingPlatform.service;

import com.proiect.licenta.InvestingPlatform.dto.PaymentInfo;
import com.proiect.licenta.InvestingPlatform.entity.Investment;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface InvestmentService {
    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;

    Investment createInvestment(Investment investment);

    void addInvestmentToUserCampaign(Investment investment, String companyId);
}
