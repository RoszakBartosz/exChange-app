package com.example.exchange_app;

import com.example.exchange_app.model.ExChangeRate;
import com.example.exchange_app.model.ExChangeRateTable;
import com.example.exchange_app.repository.ExChangingRepository;
import com.example.exchange_app.service.ImportExChangingRatesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;

class ImportExChangingRatesServiceTest {

}
