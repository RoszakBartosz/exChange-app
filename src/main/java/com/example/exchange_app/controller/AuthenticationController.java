package com.example.exchange_app.controller;

import com.example.exchange_app.model.dto.*;
import com.example.exchange_app.service.AuthenticationService;
import com.example.exchange_app.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final JwtService jwtService;
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody AuthenticationTokenRequest request) {
        return new ResponseEntity<>(service.refresh(request), HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequestDTO requestDTO){
    return new ResponseEntity<>(service.register(requestDTO), HttpStatus.CREATED);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequestDTO requestDTO){
    return new ResponseEntity<>(service.authenticate(requestDTO), HttpStatus.OK);
    }

}
