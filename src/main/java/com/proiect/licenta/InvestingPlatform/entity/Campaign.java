package com.proiect.licenta.InvestingPlatform.entity;

import com.proiect.licenta.InvestingPlatform.dto.RewardDto;
import com.proiect.licenta.InvestingPlatform.utils.Stare;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document("Campanie")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Campaign {

    @Id
    private String id;
    private String companyName;
    private String title;
    private String description;
    private String youtubeLink;
    private Stare state;
    private LocalDate dateCreated;
    private LocalDate dateEnded;
    private List<Investment> investments;
    private double minimumInvestment;
    private double goal;
    private int duration;
    private String category;
    private String holderName;
    private  String bankIndentifier;
    private String accountNumber;
    private String createdByUser;
    private List<RewardDto> rewards;
}
