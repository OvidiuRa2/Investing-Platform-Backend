package com.proiect.licenta.InvestingPlatform.service;

import com.proiect.licenta.InvestingPlatform.config.JwtService;
import com.proiect.licenta.InvestingPlatform.dao.UserRepository;
import com.proiect.licenta.InvestingPlatform.entity.AuthenticationResponse;
import com.proiect.licenta.InvestingPlatform.entity.Campaign;
import com.proiect.licenta.InvestingPlatform.entity.User;
import com.proiect.licenta.InvestingPlatform.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse save(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return null;
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Role.USER);
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(jwtToken).build();
        }
    }

    @Override
    public AuthenticationResponse authenticate(User user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        var userResponse = userRepository.findByEmail(user.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(userResponse);

        return AuthenticationResponse.builder().
                token(jwtToken)
                .email(userResponse.getEmail())
                .name(userResponse.getName())
                .phoneNumber(userResponse.getPhoneNumber())
                .password(userResponse.getPassword())
                .role(userResponse.getRole().toString())
                .investments(userResponse.getInvestments())
                .campaigns(userResponse.getCampaigns())
                .imageUrl(userResponse.getImageUrl())
                .build();

    }

    @Override
    public User findUserByInvestmentId(String investmentId) {
        return userRepository.findAll().stream()
                .filter(user -> user.getInvestments().stream()
                        .anyMatch(investment -> investment.getId().equals(investmentId)))
                .findFirst()
                .orElse(null);
    }


    @Override
    public User findUserByCompanyId(String companyId) {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            for (Campaign campaign : user.getCampaigns()) {
                if (campaign.getId().equals(companyId)) {
                    return user;
                }
            }
        }
        return null;
    }

}
