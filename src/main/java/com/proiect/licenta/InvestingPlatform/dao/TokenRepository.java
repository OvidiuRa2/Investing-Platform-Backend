package com.proiect.licenta.InvestingPlatform.dao;

import com.proiect.licenta.InvestingPlatform.entity.ValidToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends MongoRepository<ValidToken,String> {

}
