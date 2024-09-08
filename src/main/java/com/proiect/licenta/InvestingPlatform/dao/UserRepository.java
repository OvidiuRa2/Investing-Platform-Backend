package com.proiect.licenta.InvestingPlatform.dao;

import com.proiect.licenta.InvestingPlatform.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    Optional<User> findByEmail(String email);
}
