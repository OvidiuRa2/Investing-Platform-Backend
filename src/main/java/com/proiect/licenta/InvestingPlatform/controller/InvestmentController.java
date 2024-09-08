package com.proiect.licenta.InvestingPlatform.controller;

import com.proiect.licenta.InvestingPlatform.dto.PaymentInfo;
import com.proiect.licenta.InvestingPlatform.entity.Investment;
import com.proiect.licenta.InvestingPlatform.service.InvestmentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/investment")
public class InvestmentController {

    @Autowired
    public InvestmentService investmentService;


    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {
        log.info("paymentInfo.amount: " + paymentInfo.getAmount());

        PaymentIntent paymentIntent = investmentService.createPaymentIntent(paymentInfo);

        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Investment> createInvestment(@RequestBody Investment investment){
        log.info("createInvestment request {}",investment);
        Investment investmentResponse = investmentService.createInvestment(investment);
        log.info("createInvestment response {}",investmentResponse);

        return new ResponseEntity<>(investmentResponse,HttpStatus.CREATED);
    }

    @PostMapping("/{companyId}")
    public ResponseEntity<Void> addInvestmentToUserCampaign(@RequestBody Investment investment,@PathVariable String companyId){
        log.info("addInvestmentToUserCampaign investment {} id {}",investment,companyId);
        investmentService.addInvestmentToUserCampaign(investment,companyId);
        return ResponseEntity.ok().build();
    }

}
