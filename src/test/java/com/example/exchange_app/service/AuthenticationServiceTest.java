package com.example.exchange_app.service;

import com.example.exchange_app.model.User;
import com.example.exchange_app.model.dto.AuthenticationRequestDTO;
import com.example.exchange_app.model.dto.AuthenticationResponse;
import com.example.exchange_app.model.dto.AuthenticationTokenRequest;
import com.example.exchange_app.model.dto.RegisterRequestDTO;
import com.example.exchange_app.model.enums.UserRole;
import com.example.exchange_app.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void shouldRegisterUserAndReturnTokens() {
        // given
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setEmail("john@example.com");
        request.setPassword("password");

        Mockito.when(passwordEncoder.encode("password")).thenReturn("hashedPassword");
        Mockito.when(jwtService.generateToken(Mockito.any(User.class))).thenReturn("jwtToken");
        Mockito.when(jwtService.generateRefreshToken(Mockito.any(User.class))).thenReturn("refreshToken");

        // when
        AuthenticationResponse response = authenticationService.register(request);

        // then
        Assertions.assertEquals("jwtToken", response.getToken());
        Assertions.assertEquals("refreshToken", response.getRefreshToken());
        Mockito.verify(repository).save(Mockito.any(User.class));
    }
    @Test
    void shouldAuthenticateAndReturnTokens() {
        // given
        AuthenticationRequestDTO request = new AuthenticationRequestDTO();
        request.setEmail("john@example.com");
        request.setPassword("password");

        User user = User.builder()
                .email("john@example.com")
                .password("hashedPassword")
                .role(UserRole.USER)
                .build();

        Mockito.when(repository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        Mockito.when(jwtService.generateToken(user)).thenReturn("jwtToken");
        Mockito.when(jwtService.generateRefreshToken(user)).thenReturn("refreshToken");

        // when
        AuthenticationResponse response = authenticationService.authenticate(request);

        // then
        Assertions.assertEquals("jwtToken", response.getToken());
        Assertions.assertEquals("refreshToken", response.getRefreshToken());
        Mockito.verify(authenticationManager).authenticate(Mockito.any());
    }
    @Test
    void shouldRefreshTokenSuccessfully() {
        // given
        AuthenticationTokenRequest request = new AuthenticationTokenRequest();
        request.setEmail("john@example.com");
        request.setRefreshToken("validRefreshToken");

        User user = new User();
        user.setEmail("john@example.com");

        Mockito.when(jwtService.isTokenExpired("validRefreshToken")).thenReturn(false);
        Mockito.when(repository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        Mockito.when(jwtService.generateToken(user)).thenReturn("newJwtToken");

        // when
        AuthenticationResponse response = authenticationService.refresh(request);

        // then
        Assertions.assertEquals("newJwtToken", response.getToken());
        Assertions.assertEquals("validRefreshToken", response.getRefreshToken());
    }

    @Test
    void shouldThrowExceptionWhenRefreshTokenIsExpired() {
        // given
        AuthenticationTokenRequest request = new AuthenticationTokenRequest();
        request.setEmail("john@example.com");
        request.setRefreshToken("expiredToken");

        Mockito.when(jwtService.isTokenExpired("expiredToken")).thenReturn(true);

        // when + then
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            authenticationService.refresh(request);
        });
    }
}