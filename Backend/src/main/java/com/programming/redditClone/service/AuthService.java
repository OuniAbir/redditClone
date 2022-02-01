package com.programming.redditClone.service;

import com.programming.redditClone.dto.AuthenticationResponse;
import com.programming.redditClone.dto.LoginRequest;
import com.programming.redditClone.dto.RegisterRequest;
import com.programming.redditClone.exception.SpringRedditCloneException;
import com.programming.redditClone.model.NotificationEmail;
import com.programming.redditClone.model.User;
import com.programming.redditClone.model.VerificationToken;
import com.programming.redditClone.repository.UserRepository;
import com.programming.redditClone.repository.VerificationTokenRepository;
import com.programming.redditClone.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager ; // it's an interface n it has many impl, so we need to explicit the bean to use
    // we create the bean inside the security-config that extends websecurityconfigureradapter
    // so that whenever we autowired this AuthenticationManager, spring find the bean in the security-config n inject it here

    private final JwtProvider jwtProvider ;
    @Transactional
    public void signup(RegisterRequest registerRequest) {
        //save the new user from the register request
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());

//        encode the password using Bcrypt Algorithm
        user.setPassword(encodePassword(registerRequest.getPassword()));
//        initially user is not enabled, he needs to ping the activation mail send just later
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


    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(()-> new SpringRedditCloneException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    void fetchUserAndEnable(VerificationToken verificationToken) {
        User user = userRepository.findById( verificationToken.getUser().getId()).orElseThrow(()-> new SpringRedditCloneException("user not found"));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        // logic to authenticate the user
        // authManager verify our credentials n if it matches thn create a HWT n send it as a response to the client
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtProvider.generateToken(auth);
        return new AuthenticationResponse(token, loginRequest.getUsername());
    }
}
