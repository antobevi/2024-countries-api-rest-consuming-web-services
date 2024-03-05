package com.antobevi.CountriesAPIRestConsumingWebServices.model.apiresponse;

import lombok.Data;

import java.util.Map;

@Data
public class CountryData { // Me permite almacenar temporalmente la info recibida en el JSON para luego pasarla a una clase java
    // Se recomienda que los campos coincidan con el JSON de la API Rest
    private Map<String, String> languages;
    private Map<String, String> maps;

}
