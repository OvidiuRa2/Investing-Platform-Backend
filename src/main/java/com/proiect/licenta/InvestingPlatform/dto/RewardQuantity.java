package com.proiect.licenta.InvestingPlatform.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RewardQuantity {
    private long quantity;
    private RewardDto rewardSelected;
}
