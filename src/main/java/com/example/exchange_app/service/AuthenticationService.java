package com.example.exchange_app.service;

import com.example.exchange_app.model.User;
import com.example.exchange_app.model.dto.AuthenticationRequestDTO;
import com.example.exchange_app.model.dto.AuthenticationResponse;
import com.example.exchange_app.model.dto.AuthenticationTokenRequest;
import com.example.exchange_app.model.dto.RegisterRequestDTO;
import com.example.exchange_app.model.enums.UserRole;
import com.example.exchange_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequestDTO requestDTO){
        User build = User.builder()
                .firstname(requestDTO.getFirstname())
                .lastname(requestDTO.getLastname())
                .email(requestDTO.getEmail())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .role(UserRole.USER)
                .build();
        var refreshToken = jwtService.generateRefreshToken(build);
        var jwtToken = jwtService.generateToken(build);
        repository.save(build);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();


    }
    public AuthenticationResponse authenticate(AuthenticationRequestDTO requestDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getEmail(),
                        requestDTO.getPassword()));
        User user = repository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String jwtRefreshToken = jwtService.generateRefreshToken(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .build();
    }

    public AuthenticationResponse refresh(AuthenticationTokenRequest requestDTO) {
        System.out.println(requestDTO.getRefreshToken());
        if (!jwtService.isTokenExpired(requestDTO.getRefreshToken())||requestDTO.getRefreshToken()==null) {
            User user = repository.findByEmail(requestDTO.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .refreshToken(requestDTO.getRefreshToken())
                    .build();
        } else {
            throw new NoSuchElementException("this refresh token is expired");
        }
    }
}
