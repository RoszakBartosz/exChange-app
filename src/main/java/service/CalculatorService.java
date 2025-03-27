package service;

import model.ExChangeRate;
import model.ExChangeRateTable;
import org.hibernate.query.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

@Service
public class CalculatorService {
    private final WebClient webClient;

    public CalculatorService(WebClient.Builder builder){
        this.webClient = builder.baseUrl("https://api.nbp.pl/api").build();
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


    public void saveToDBRates(){
        Mono<List<ExChangeRate>> exchangeRates = getExchangeRates();
        List<ExChangeRate> exChangeRateList = exchangeRates.block();
    }
}
