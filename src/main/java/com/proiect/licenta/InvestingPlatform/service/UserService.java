package com.proiect.licenta.InvestingPlatform.service;

import com.proiect.licenta.InvestingPlatform.entity.AuthenticationResponse;
import com.proiect.licenta.InvestingPlatform.entity.User;


public interface UserService {

   public AuthenticationResponse save(User user);

    User findUserByCompanyId(String companyName);

    AuthenticationResponse authenticate(User user);

    User findUserByInvestmentId(String investmentId);

}
