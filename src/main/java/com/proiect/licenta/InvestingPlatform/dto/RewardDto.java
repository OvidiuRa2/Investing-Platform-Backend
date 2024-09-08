package com.proiect.licenta.InvestingPlatform.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Data
@Getter
@Setter
@NoArgsConstructor
public class RewardDto {
    private String name;
    private double price;
    private String description;
    private String imageUrl;
    }
