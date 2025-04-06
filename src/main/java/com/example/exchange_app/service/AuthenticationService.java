package com.example.exchange_app.service;

import com.example.exchange_app.model.User;
import com.example.exchange_app.model.dto.AuthenticationRequestDTO;
import com.example.exchange_app.model.dto.AuthenticationResponse;
import com.example.exchange_app.model.dto.RegisterRequestDTO;
import com.example.exchange_app.model.enums.UserRole;
import com.example.exchange_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequestDTO requestDTO){
        User build = User.builder()
                .firstname(requestDTO.getFirstname())
                .lastname(requestDTO.getLastname())
                .email(requestDTO.getEmail())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .role(UserRole.USER)
                .build();

        var jwtToken = jwtService.generateToken(build);
        repository.save(build);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequestDTO requestDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getEmail(),
                        requestDTO.getPassword()
                )
        );
        var user = repository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
