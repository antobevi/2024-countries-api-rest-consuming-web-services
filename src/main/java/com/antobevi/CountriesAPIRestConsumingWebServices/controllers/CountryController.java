package com.antobevi.CountriesAPIRestConsumingWebServices.controllers;

import com.antobevi.CountriesAPIRestConsumingWebServices.webservices.CountrySoapClient;
import localhost.ws.ObtenerPaisResponse;
import localhost.ws.Pais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {
    private final CountrySoapClient countrySoapClient;

    @Autowired
    public CountryController (CountrySoapClient countrySoapClient) {
        this.countrySoapClient = countrySoapClient;
    }

    @GetMapping("getCountry") // http://localhost:8080/countries-api/getCountry?name=Argentina
    public Pais getCountry(@RequestParam String name) {
        ObtenerPaisResponse response = countrySoapClient.getCountry(name);
        Pais country = new Pais();

        country.setNombre(response.getPais().getNombre());
        country.setCapital(response.getPais().getCapital());
        country.setMoneda(response.getPais().getMoneda());
        country.setPoblacion(response.getPais().getPoblacion());
        country.setBandera(response.getPais().getBandera());

        return country;
    }

}
