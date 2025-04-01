package com.example.exchange_app.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.example.exchange_app.model.ExChangeRate;
import com.example.exchange_app.model.ExChangeRateTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.example.exchange_app.repository.ExChangingRepository;

import java.util.List;

@Service
public class ImportExChangingRatesService {
    private WebClient webClient;
    private final ExChangingRepository repository;
    private static final Logger log = LoggerFactory.getLogger(ImportExChangingRatesService.class);

    public ImportExChangingRatesService(WebClient.Builder builder, ExChangingRepository repository){
        this.webClient = builder.baseUrl("https://api.nbp.pl/api").build();
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        saveToDBRates();
    }

    public Mono<List<ExChangeRate>> getExchangeRates() {
        Mono<List<ExChangeRate>> listMono = webClient.get()
                .uri("/exchangerates/tables/A?format=json")
                .retrieve()
                .bodyToFlux(ExChangeRateTable.class)
                .flatMapIterable(ExChangeRateTable::getRates)
                .collectList();
        return listMono;
    }
    @Transactional
    @Scheduled(cron = "0 */10 * * * *")
    public void saveToDBRates(){
        getExchangeRates()
                .doOnError(e -> log.error("Error fetching exchange rates: ", e)) // Logowanie błędów
                .onErrorResume(e -> Mono.empty()) // Zapewnia, że błędy nie zatrzymają całej aplikacji
                .subscribe(exChangeRateList -> {
                    if (exChangeRateList != null && !exChangeRateList.isEmpty()) {
                        repository.deleteAll();
                        repository.saveAll(exChangeRateList);
                    } else {
                        log.warn("No exchange rates to save");
                    }
                });
    }

}
