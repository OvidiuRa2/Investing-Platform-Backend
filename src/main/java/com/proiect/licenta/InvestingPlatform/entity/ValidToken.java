package com.proiect.licenta.InvestingPlatform.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Getter
@Setter
public class ValidToken {
    @Id
    private String token;
    private boolean validated;
}
