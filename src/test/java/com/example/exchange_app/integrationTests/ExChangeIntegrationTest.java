package com.example.exchange_app.integrationTests;

import com.example.exchange_app.model.ExChangeRate;
import com.example.exchange_app.model.User;
import com.example.exchange_app.model.dto.AuthenticationResponse;
import com.example.exchange_app.model.dto.RegisterRequestDTO;
import com.example.exchange_app.model.dto.RequestExChangeDTO;
import com.example.exchange_app.model.dto.ResponseDTOExChangeRate;
import com.example.exchange_app.model.enums.UserRole;
import com.example.exchange_app.repository.ExChangingRepository;
import com.example.exchange_app.service.AuthenticationService;
import com.example.exchange_app.service.ExChangeService;
import com.example.exchange_app.service.JwtService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ExChangeIntegrationTest {

    @Autowired
    private ExChangingRepository repository;

    @Autowired
    private ExChangeService exChangeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("test-db")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void datasourceConfig(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    @Order(1)
    void shouldExchangeFromPLNToUSD() throws Exception {
        // given
        RequestExChangeDTO request = RequestExChangeDTO.builder()
                .codeCurrencyFrom("EUR")
                .codeCurrencyTo("USD")
                .valueFrom(BigDecimal.valueOf(200))
                .build();

        // token JWT

        AuthenticationResponse register = authenticationService.register(
                RegisterRequestDTO.builder()
                        .email("sadas@a.a")
                        .password("adsas")
                        .lastname("sdfsf")
                        .firstname("dsgs")
                        .build());
        String token = register.getToken();

        // when + then
        mockMvc.perform(post("/ex/exchange")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codeFrom").value("EUR"))
                .andExpect(jsonPath("$.codeTo").value("USD"))
            .andExpect(jsonPath("$.amountTo").isNumber());
    }




    @Test
    @Order(2)
    void shouldThrowExceptionWhenCurrencyCodeNotFound() {
        // given
        RequestExChangeDTO request = RequestExChangeDTO.builder()
                .codeCurrencyFrom("PLN")
                .codeCurrencyTo("XYZ")
                .valueFrom(BigDecimal.valueOf(100))
                .build();

        // when + then
        assertThrows(NullPointerException.class, () -> exChangeService.exChange(request));
    }
}

