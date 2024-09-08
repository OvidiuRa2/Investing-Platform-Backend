package com.proiect.licenta.InvestingPlatform.dao;

import com.proiect.licenta.InvestingPlatform.entity.Investment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "investitii")
public interface InvestmentRepository extends MongoRepository<Investment,String> {

}
