package com.proiect.licenta.InvestingPlatform.service;

import com.proiect.licenta.InvestingPlatform.dao.InvestmentRepository;
import com.proiect.licenta.InvestingPlatform.dao.UserRepository;
import com.proiect.licenta.InvestingPlatform.dto.PaymentInfo;
import com.proiect.licenta.InvestingPlatform.entity.Campaign;
import com.proiect.licenta.InvestingPlatform.entity.Investment;
import com.proiect.licenta.InvestingPlatform.entity.User;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvestmentServiceImpl implements InvestmentService{

    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private UserRepository userRepository;

    public InvestmentServiceImpl(InvestmentRepository investmentRepository,
                                 @Value("${stripe.key.secret}") String secretKey    ) {
        this.investmentRepository = investmentRepository;
        Stripe.apiKey = secretKey;

    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String,Object> params = new HashMap<>();
        params.put("amount",paymentInfo.getAmount());
        params.put("currency",paymentInfo.getCurrency());
        params.put("payment_method_types",paymentMethodTypes);
        params.put("description","Startup Pulse purchase");
//        params.put("receipt_email",paymentInfo.getReceiptEmail());

        return PaymentIntent.create(params);
    }

    @Override
    public Investment createInvestment(Investment investment) {
        return investmentRepository.save(investment);
    }

    @Override
    public void addInvestmentToUserCampaign(Investment investment, String companyId) {
        List<User> users = userRepository.findAll();

        users.forEach(user -> {
            user.getCampaigns().forEach(campaign -> {
                if (campaign.getId().equals(companyId)) {
                    campaign.getInvestments().add(investment);
                    userRepository.save(user);
                }
            });
        });
    }
}
