package com.proiect.licenta.InvestingPlatform.entity;

import com.proiect.licenta.InvestingPlatform.dto.RewardDto;
import com.proiect.licenta.InvestingPlatform.dto.RewardQuantity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Document("Investitie")
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Investment {
    @Id
    private String id;
    private LocalDate date;
    private double amountInvestedStartup;
    private double amountInvestedPlatform;
    private List<RewardQuantity> rewardsSelected;

}
