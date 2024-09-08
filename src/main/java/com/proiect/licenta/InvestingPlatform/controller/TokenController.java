package com.proiect.licenta.InvestingPlatform.controller;

import com.proiect.licenta.InvestingPlatform.dao.TokenRepository;
import com.proiect.licenta.InvestingPlatform.entity.ValidToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/token")
public class TokenController {

    @Autowired
    private TokenRepository tokenRepository;
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestParam("token")String token){
        var validToken = tokenRepository.findById(token);
        if(!validToken.get().isValidated()){
            validToken.get().setValidated(true);
            tokenRepository.save(validToken.get());
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
}
