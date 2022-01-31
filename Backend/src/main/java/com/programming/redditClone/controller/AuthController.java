package com.programming.redditClone.controller;

import com.programming.redditClone.dto.RegisterRequest;
import com.programming.redditClone.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    protected ResponseEntity<String> signup(
            @RequestBody RegisterRequest registerRequest
    ) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration successful", HttpStatus.OK);
        // we got the return message after 10 sec which is expensive
        // It's because after clicking on sign up in the UI :
        // we are saving user in Db
        // then we are contacting an external mail server to send a verification email
        // what we can do is to send the verification email asynchronously by running this code in a different thread
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully ", HttpStatus.OK);
    }

}
