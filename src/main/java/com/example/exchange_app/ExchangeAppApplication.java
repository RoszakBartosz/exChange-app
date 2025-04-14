package com.example.exchange_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class ExchangeAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeAppApplication.class, args);
	}

//	Stworz restowe api ktore pozwala obliczac kursy wymiany walut (realnie mozesz sie polaczyc z prawdziwym api do wymiany kursow walut ale zacacheuj polaczenie aby pytania o kurs danej pary walut nie byl czesciej niz raz na 10 minut)
//
//	endpoint:
//	@POST /api/v1/calculator/_exchange
//	body: { amount, currencyFrom, currencyTo }
//	response:
//			200 OK:
//	body: {amount: ... }
//	lub
//400 BAD REQUEST:
//	body: { message: invalid currency|missing currency|missing amount }
//
//	kazde zapytanie generuje historie w bazie danych
//	dlatego mamy drugi endpoint
//
//	@GET /api/v1/calculator/exchanges
//	ktory zwraca nam stronę [paginacja] z zapytaniami ktore generowal uzytkownik o kursy walut
//	w sklad odpowiedzi wchodzi: kwota, waluta od, waluta do, data i czas zapytania. }
//
//mozemy za pomoca request parametrow wyszukiwac po: walutaZ, walutaDo, dataOd, dataDo, kwotaOd, kwotaDo w dowolnej permutacji
//
//mozna tez generowac raporty na podstawie histori
//
//endpoint:
//@GET /api/v1/reports/{reportName}
//
//i tak wspieramy kilka reporow:
//HIGH_AMOUNT_LAST_MONTH - ktory zwraca pogrupowane transakcje z ostatniego miesiaca dla transakcji ktorych ogolna wartosc byla wieksza niz 15 tys amount w dniu transakcji.
//		GROUP_BY_CURRENCY_FROM_LAST_MONTH - ktory zwraca pogrupowane transakcje z ostatniego miesiaca po currencyFRom np: EURO bylo 10, USD 20, CHF 15 itp.
//		GROUP_BY_CURRENCY_TO_LAST_MONTH - analogicznie..

//	GROUP_BY_CURRENCY_FROM_LAST_MONTH


//	historylog CF pln CT usd amount 30000
//	history log > INTERVAL(1month ago)
//
//
//	PLN {
//		transakcja1 CF: 20
//		transakcja2 CF: 30
//	}
//	USD {
//		transakcja1 CF: 20
//		transakcja2 CF: 30
//	}

	//reporty z query
	//test integracyjny przez mockmvc
	//uruchomic apke przez dockerfile
	//docker-compose dodac volume i czytac tam


// LISTA CO MUSI BYC ZAIMPLEMTENOWANE:
// testy unit, intrgracyjne z Testconatainers
	// spring secuirty jwt (enpdoint do tworzenia tokenow i refresh tokenow)
	// kazda nowa fuknjconalsc (np dodanie spring security jwt, napsianie testow)
	// tworzona jako nowa branch i nastpenie mergowana do mastera, nie usuwany branchy po zmergowaniu dio mastera
	// commity i pull requesty odpowiednie komentarze po ang i nzwy a nie rucham baske 123
	// branch tworzymy i robimy commity i wyoychamy do github za pomoca komend w temrinalu a nie intefeju
	// pull requesty sa mergowane wiadomo w githubie
	// cache za pomocą spring boot cache, chyba ze chcesz sie oopisac to postaw se redisa
	// i wyminana walut nie musi byc kazdej waluty znajdz po prostu api (jest strona na npb z dostaepnym api wymiany walut)
	// i po prostu zrob wyimany z pl na inne waluty i z innych na pl jesli sie da
	// dockeryzacja aplikacji
	//schedlock
	// swagger
	// obsluga n+1
// query dsl§
}
