package com.proiect.licenta.InvestingPlatform.dao;

import com.proiect.licenta.InvestingPlatform.entity.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(path = "campaign")
public interface CampaignsRepository extends MongoRepository<Campaign, String> {
    Campaign findByTitle(String title);

    Page<Campaign> findAll(Pageable pageable);
}
