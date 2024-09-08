package com.proiect.licenta.InvestingPlatform.controller;

import com.proiect.licenta.InvestingPlatform.config.JwtService;
import com.proiect.licenta.InvestingPlatform.dao.CampaignsRepository;
import com.proiect.licenta.InvestingPlatform.dao.UserRepository;
import com.proiect.licenta.InvestingPlatform.dto.UserDto;
import com.proiect.licenta.InvestingPlatform.dto.UsernameClaim;
import com.proiect.licenta.InvestingPlatform.entity.AuthenticationResponse;
import com.proiect.licenta.InvestingPlatform.entity.Campaign;
import com.proiect.licenta.InvestingPlatform.entity.User;
import com.proiect.licenta.InvestingPlatform.service.UserService;
import com.proiect.licenta.InvestingPlatform.utils.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CampaignsRepository campaignsRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> createUser(@RequestBody UserDto userDto) {
        log.info("login request {}", userDto);
        User user = new User("http://bootdey.com/img/Content/avatar/avatar1.png", userDto.getEmail(), userDto.getName(), userDto.getPassword(), userDto.getPhoneNumber(), new ArrayList<>(), new ArrayList<>(), Role.USER);
        AuthenticationResponse serviceResponse = userService.save(user);
        log.info("createUser response {}", serviceResponse);
        return new ResponseEntity<>(serviceResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserDto userDto) {
        log.info("login request {}", userDto);
        User user = new User("", userDto.getEmail(), "", userDto.getPassword(), "", new ArrayList<>(), new ArrayList<>(), Role.USER);
        AuthenticationResponse serviceResponse = userService.authenticate(user);
        log.info("login response {}", serviceResponse);
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        log.info("saveUser request{}", user);
        User user1 = userRepository.save(user);
        log.info("saveUser response{}", user);
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody UserDto updatedUser) {
        // Verifică dacă utilizatorul cu ID-ul specificat există în sistem
        User existingUser = userRepository.findById(userId).orElse(null);

        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        if (updatedUser.getEmail() != "") {
            User userWithNewEmail = userRepository.findByEmail(updatedUser.getEmail()).orElse(null);
            if (userWithNewEmail != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .build();
            }
        }

        // Actualizează datele utilizatorului existent cu cele primite în corpul cererii
        if (updatedUser.getName() != "") {
            existingUser.setName(updatedUser.getName());
        }

        if (updatedUser.getPhoneNumber() != "") {
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        }

        if (updatedUser.getPassword() != "") {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        if (updatedUser.getImageUrl() != "") {
            existingUser.setImageUrl(updatedUser.getImageUrl());
        }

        if (updatedUser.getEmail() != "") {
            existingUser.getCampaigns().forEach(c -> c.setCreatedByUser(updatedUser.getEmail()));

            List<Campaign> campaigns = campaignsRepository.findAll();

            campaigns.forEach(c -> {
                if (c.getCreatedByUser().equals(existingUser.getEmail())) {
                    c.setCreatedByUser(updatedUser.getEmail());
                    campaignsRepository.save(c);
                }
            });

            userRepository.delete(existingUser);

            existingUser.setEmail(updatedUser.getEmail());
        }

        log.info("Existing user: {}", existingUser);
        log.info("updatedUser: {}", updatedUser);

        User savedUser = userRepository.save(existingUser);

        return ResponseEntity.ok(savedUser);
    }


    @GetMapping("/{companyId}")
    public ResponseEntity<User> findUserByCompanyId(@PathVariable String companyId) {
        return ResponseEntity.ok(userService.findUserByCompanyId(companyId));
    }

    @GetMapping("/findByEmail")
    public ResponseEntity<User> findUser(@RequestParam("email") String email) {
        log.info("findUserByEmail {}", email);
        var response = userRepository.findByEmail(email).get();
        log.info("findUserByEmail {}", response);

        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<User>> findALl() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/find/{investmentId}")
    public ResponseEntity<User> findUserByInvestmentId(@PathVariable String investmentId) {
        log.info("findUserByInvestmentId request {}", investmentId);
        var response = userService.findUserByInvestmentId(investmentId);
        log.info("findUserByInvestmentId response {}", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam("email") String email, @RequestBody UserDto updatedUser) {
        log.info("resetPassword start");
        User existingUser = userRepository.findById(email).orElse(null);

        if (updatedUser.getPassword() != "") {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        userRepository.save(existingUser);

        log.info("resetPassword end: {}, pass: {}",existingUser,updatedUser.getPassword());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password reset successful");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/extract-username")
    public ResponseEntity<?> extractUsername(@RequestParam("token") String token){
        var response = jwtService.extractUsername(token);
        return ResponseEntity.ok(UsernameClaim.builder().email(response).build());
    }
}
