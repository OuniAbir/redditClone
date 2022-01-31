package com.programming.redditClone.service;

import com.programming.redditClone.dto.RegisterRequest;
import com.programming.redditClone.model.NotificationEmail;
import com.programming.redditClone.model.User;
import com.programming.redditClone.model.VerificationToken;
import com.programming.redditClone.repository.UserRepository;
import com.programming.redditClone.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        //save the new user from the register request
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());

//        encode the password using Bcrypt Algorithm
        user.setPassword(encodePassword(registerRequest.getPassword()));
//        initially user is not enabled, he need to ping the activation mail send just later
        user.setEnabled(false);
        user.setCreatedDate(Instant.now());
        userRepository.save(user);

//        send a verification link for account verification :  authAPI+generated Token
        String token = generateVerificationToken(user);
        mailService.sendMail(
                new NotificationEmail(
                        "Please Activate your Account ",
                        "please Click on this url to activate your account " + "http://localhost:8080/api/auth/accountVerification/" + token,
                        user.getEmail()
                )
        );
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;

    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


}
