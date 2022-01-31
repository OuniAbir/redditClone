package com.programming.redditClone.controller;

import com.programming.redditClone.dto.RegisterRequest;
import com.programming.redditClone.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    }
}
