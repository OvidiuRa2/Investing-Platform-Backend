package com.proiect.licenta.InvestingPlatform.service;

import com.proiect.licenta.InvestingPlatform.dao.CampaignsRepository;
import com.proiect.licenta.InvestingPlatform.entity.Campaign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignsServiceImpl implements CampaignsService {

    @Autowired
    private CampaignsRepository campaignsRepository;

    @Override
    public Campaign save(Campaign campaign) {
        return campaignsRepository.save(campaign);
    }

    @Override
    public Page<Campaign> filterCampaigns(Pageable pageable, String title, String category) {
        List<Campaign> campaigns = campaignsRepository.findAll();

        List<Campaign> filteredByTitle = filterByTitle(campaigns,title);
        List<Campaign> filteredByCategoryAndTitle = filterByCategory(filteredByTitle,category);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredByCategoryAndTitle.size());
        List<Campaign> pagedCampaigns;

        if (start > filteredByCategoryAndTitle.size()) {
            pagedCampaigns = new ArrayList<>();
        } else {
            pagedCampaigns = filteredByCategoryAndTitle.subList(start, end);
        }

        return new PageImpl<>(pagedCampaigns, pageable, filteredByCategoryAndTitle.size());

    }

    private List<Campaign> filterByTitle(List<Campaign> campaigns, String title) {
        return campaigns.stream()
                .filter(campaign -> {
                    if (title != null && !title.trim().isEmpty()) {
                        return campaign.getTitle().toLowerCase().contains(title.toLowerCase().trim());

                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    private List<Campaign> filterByCategory(List<Campaign> campaigns, String category) {
        return campaigns.stream()
                .filter(campaign -> {
                    if (category != null && !category.trim().isEmpty()) {
                        return campaign.getCategory().equals(category);
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
