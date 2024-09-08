package com.proiect.licenta.InvestingPlatform.controller;

import com.proiect.licenta.InvestingPlatform.config.JwtService;
import com.proiect.licenta.InvestingPlatform.dao.TokenRepository;
import com.proiect.licenta.InvestingPlatform.dao.UserRepository;
import com.proiect.licenta.InvestingPlatform.dto.EmailRequest;
import com.proiect.licenta.InvestingPlatform.entity.ValidToken;
import com.proiect.licenta.InvestingPlatform.service.EmailService;
import com.proiect.licenta.InvestingPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {

    String responseMessage = """
                Hello [Client's Name] 👋,
                               
                Thank you for reaching out to StartupPulse. We sincerely appreciate you taking the time to contact us.
                               
                This is to confirm that we have received your message through our contact form. Your inquiry is important to us, and we assure you that we are diligently reviewing it.
                               
                Our team is currently working on addressing your query and will respond to you promptly. We aim to provide you with the best possible assistance and ensure that all your questions are thoroughly answered.
                               
                If you have any urgent matters or additional information to share, please feel free to reach out to us directly at +40712322768.
                               
                Once again, thank you for contacting StartupPulse. We look forward to assisting you and will be in touch with you shortly.
                               
                Best regards,     
                The Startup Pulse Team 😊
                """;

    String responseMessageResetPassword = """
            Hello 👋,
                        
            We received a request to reset the password associated with your account. If you did not make this request, please disregard this email.
                        
            To reset your password, please click on the link below:
                        
            [Reset Link]
                        
            If the above link does not work, copy and paste the link into your browser.
                        
            Please note that this link is only valid for a limited time. If you did not initiate this request or no longer require assistance, you can safely ignore this message.
                        
            Best regards,     
            The Startup Pulse Team 😊
                """;

    String responseMessageSuccessfulInvestment = """
        Hello [Client's Name]👋,
        
        Congratulations on your successful investment with Startup Pulse! 🎉
        
        We're thrilled to inform you that your investment has been processed successfully.
        
        For more details, you can keep track of your investments in the Portfolio section.
        
        Thank you for being a part of our vibrant community of investors supporting innovative startups.
        
        If you have any questions or need further assistance, feel free to reach out to our support team.
        
        Best regards,
        The Startup Pulse Team 😊
        """;

    String responseMessageSuccessfulAccountCreation= """
            Dear [Client's Name],
                        
            We are thrilled to inform you that your account has been successfully created. You can now enjoy full access to all the features and benefits of our platform.
                        
            To get started, simply log in using your email and the password you set during the registration process. If you ever need to update your account details or change your password, you can do so easily from your profile page.
                        
            For your convenience, here is the login link: http://localhost:4200/login.
                        
            If you have any questions or need assistance, our support team is here to help. Please feel free to contact us at support@pulse.com or +40712322768.
                        
            We appreciate you choosing Startup Pulse and are excited to have you with us.
                        
            Best regards,
            The Startup Pulse Team 😊""";

    @Autowired
    private EmailService emailService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;

    @PostMapping("send-email")
    public ResponseEntity<Void> sendEmail(@RequestBody EmailRequest request) {
        // Send email to company
        emailService.sendEmail("radoiovidiu21@stud.ase.ro", "New message from customer", request.getMessage());

       var response =  responseMessage.replace("[Client's Name]", request.getName());

        // Send email to customer
        emailService.sendEmail(request.getEmail(), "Message Confirmation - StartupPulse ",   response);

        return ResponseEntity.ok().build();
    }

    @PostMapping("send-email/resetPassword")
    public ResponseEntity<Void> sendEmailToResetPassword(@RequestBody EmailRequest request) {
        var user = userRepository.findByEmail(request.getEmail());
        String token = jwtService.generateToken(user.get());
        tokenRepository.save(new ValidToken(token,false));
       var response = responseMessageResetPassword.replace("[Reset Link]","http://localhost:4200/reset-password?token=" + token);

        emailService.sendEmail(request.getEmail(), "Password Reset - StartupPulse",   response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("send-email/successfulInvestment")
    public ResponseEntity<Void> sendEmailToSuccessfulInvestment(@RequestBody EmailRequest request) {

        var response =  responseMessageSuccessfulInvestment.replace("[Client's Name]", request.getName());

        emailService.sendEmail(request.getEmail(), "Successful Investment - StartupPulse",   response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("send-email/successfulAccountCreation")
    public ResponseEntity<Void> sendEmailToSuccessfulAccountCreation(@RequestBody EmailRequest request) {

        var response =  responseMessageSuccessfulAccountCreation.replace("[Client's Name]", request.getName());

        emailService.sendEmail(request.getEmail(), "Welcome to Startup Pulse! Your Account is Ready",   response);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/empty")
    public ResponseEntity<Void>empty(){
        return ResponseEntity.ok().build();
    }
}
