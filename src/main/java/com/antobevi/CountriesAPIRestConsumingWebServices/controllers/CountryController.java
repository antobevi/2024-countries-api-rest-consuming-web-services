package com.antobevi.CountriesAPIRestConsumingWebServices.controllers;

import com.antobevi.CountriesAPIRestConsumingWebServices.model.CountryDTO;
import com.antobevi.CountriesAPIRestConsumingWebServices.model.apiresponse.CountryData;
import com.antobevi.CountriesAPIRestConsumingWebServices.webservices.apirest.CountryRestService;
import com.antobevi.CountriesAPIRestConsumingWebServices.webservices.soap.CountrySoapClient;
import localhost.ws.ObtenerPaisResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController { // si uno de los 2 servicios se cae, consumimos el que esta activo
    private final CountrySoapClient countrySoapClient; // Consumiendo un SOAP
    private final CountryRestService countryRestService; // Consumimos un API Rest
    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    public CountryController (CountrySoapClient countrySoapClient, CountryRestService countryRestService) {
        this.countrySoapClient = countrySoapClient;
        this.countryRestService = countryRestService;
    }

    /* Obtenemos el pais por parte del soap
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
    */

    // Obtenemos el pais del primer servicio que este activo (no importa si uno de los 2 se cae)
    @GetMapping("/getCountry") // http://localhost:8080/countries-api/getCountry?name=Argentina
    public CountryDTO getCountry(@RequestParam String name) {
        ObtenerPaisResponse soapResponse = getSOAPData(name);
        CountryData apiResponse = getAPIRestData(name);

        return buildCountryDTO(soapResponse, apiResponse);
    }

    // Obtenemos el pais por parte del SOAP
    private ObtenerPaisResponse getSOAPData(String name) {
        try {
            return countrySoapClient.getCountry(name);
        } catch (Exception e) {
            logger.error("No information was obtained from the SOAP", e);

            return null;
        }
    }

    // Obtenemos el pais por parte de la API Rest
    private CountryData getAPIRestData(String name) {
        try {
            return countryRestService.getCountryInfoAPI(name);
        } catch (Exception e) {
            logger.error("No information was obtained from the API Rest", e);

            return null;
        }
    }

    // Generamos el response final a partir del response del servicio que este activo
    private CountryDTO buildCountryDTO(ObtenerPaisResponse soapResponse, CountryData apiResponse) {
        // a partir del pais que devuelve el soap y a partir del pais que devuelve la api retornamos un CountryDTO que contendra toda la info
        CountryDTO countryDTO = new CountryDTO();

        if(soapResponse != null) {
            countryDTO.setName(soapResponse.getPais().getNombre());
            countryDTO.setCurrency(soapResponse.getPais().getMoneda());
            countryDTO.setCapital(soapResponse.getPais().getCapital());
            countryDTO.setPopulationDensity(soapResponse.getPais().getPoblacion());
            countryDTO.setFlag(soapResponse.getPais().getBandera());
        } else {
            logger.error("SOAP does not respond");
        }

        if(apiResponse != null) {
            countryDTO.setLanguages(apiResponse.getLanguages());
            countryDTO.setMaps(apiResponse.getMaps());
        } else {
            logger.error("API Rest does not respond");
        }

        return countryDTO;
    }

}
