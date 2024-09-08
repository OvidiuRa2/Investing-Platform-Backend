package com.proiect.licenta.InvestingPlatform.service;

import com.proiect.licenta.InvestingPlatform.entity.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

public interface CampaignsService {
    Campaign save(Campaign campaign);

    Page<Campaign> filterCampaigns(Pageable pageable, String title, String category);

}