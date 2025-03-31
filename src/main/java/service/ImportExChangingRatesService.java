package service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import model.ExChangeRate;
import model.ExChangeRateTable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import repository.ExChangingRepository;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ImportExChangingRatesService {
    private WebClient webClient;
    private final ExChangingRepository repository;

    @PostConstruct
    public void init(){
        saveToDBRates();
    }

    public ImportExChangingRatesService(WebClient.Builder builder, ExChangingRepository repository){
        this.webClient = builder.baseUrl("https://api.nbp.pl/api").build();
        this.repository = repository;
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
        Mono<List<ExChangeRate>> exchangeRates = getExchangeRates();
        List<ExChangeRate> exChangeRateList = exchangeRates.block();
        if (exChangeRateList==null){
            throw new NoSuchElementException("Empty list of exchangeRates");
        }
        repository.saveAll(exChangeRateList);
    }

}
