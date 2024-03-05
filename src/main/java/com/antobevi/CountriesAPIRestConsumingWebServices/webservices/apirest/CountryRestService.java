package com.antobevi.CountriesAPIRestConsumingWebServices.webservices.apirest;

import com.antobevi.CountriesAPIRestConsumingWebServices.model.apiresponse.CountryData;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CountryRestService {
    private static String API_URL = "https://restcountries.com/v3.1/name/{country}?fields=languages,maps"; // endpoint
    private final WebClient webClient;

    public CountryRestService (WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(API_URL).build();
    }

    public CountryData getCountryInfoAPI(String country) { // utilizamos web flux

        return webClient.get().uri(API_URL, country)
                .accept(MediaType.APPLICATION_JSON).retrieve()
                .bodyToFlux(CountryData.class).blockLast(); // se bloquea en caso de que la ultima respuesta sea invalida
    }

}
