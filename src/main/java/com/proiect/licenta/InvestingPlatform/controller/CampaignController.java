package com.proiect.licenta.InvestingPlatform.controller;

import com.proiect.licenta.InvestingPlatform.dao.CampaignsRepository;
import com.proiect.licenta.InvestingPlatform.entity.Campaign;
import com.proiect.licenta.InvestingPlatform.service.CampaignsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/campaign")
public class CampaignController {

    @Autowired
    private CampaignsRepository campaignsRepository;
    @Autowired
    private CampaignsService campaignsService;


    @GetMapping("/{id}")
    public ResponseEntity<Campaign> findById(@PathVariable String id) {
        return ResponseEntity.ok(campaignsRepository.findById(id).get());
    }

    @GetMapping("/search/findByTitlu")
    public ResponseEntity<Campaign> getCampaignByTitle(@RequestParam("titlu") String titlu) {
        Campaign campaign = campaignsRepository.findByTitle(titlu);
        if (campaign != null) {
            return new ResponseEntity<>(campaign, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Campaign>> findAll(Pageable pageable) {
        Page<Campaign> campaigns = campaignsRepository.findAll(pageable);
        return new ResponseEntity<>(campaigns, HttpStatus.OK);
    }

    @GetMapping("/findCampaign/{investmentId}")
    public ResponseEntity<Campaign> findCampaignByInvestmentId(@PathVariable String investmentId) {
        List<Campaign> campaigns = campaignsRepository.findAll();
        for (Campaign campaign : campaigns) {
            for (var investment : campaign.getInvestments()) {
                if (investment.getId().equals(investmentId)) {
                    return ResponseEntity.ok(campaign);
                }
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Campaign> saveCampaign(@RequestBody Campaign campaign) {
        System.out.println("saveCampaign " + campaign);
        return new ResponseEntity<>(campaignsService.save(campaign), HttpStatus.CREATED);

    }

    @GetMapping("/search")
    public ResponseEntity<Page<Campaign>> filterCampaigns(Pageable pageable, @RequestParam("title") String title, @RequestParam("category") String category) {
        Page<Campaign> campaigns = campaignsService.filterCampaigns(pageable,title,category);
        return new ResponseEntity<>(campaigns, HttpStatus.OK);
    }
}
