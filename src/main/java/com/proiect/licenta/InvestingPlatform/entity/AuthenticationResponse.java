package com.proiect.licenta.InvestingPlatform.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthenticationResponse {
    private String token;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;
    private List<Campaign> campaigns;
    private List<Investment> investments;
    private String imageUrl;
}
