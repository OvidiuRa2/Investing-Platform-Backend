package com.proiect.licenta.InvestingPlatform.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmailRequest {
    private String name;
    private String email;
    private String message;
}
