package com.proiect.licenta.InvestingPlatform.dto;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class    UserDto {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String imageUrl;
}
