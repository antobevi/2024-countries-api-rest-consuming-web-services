package com.antobevi.CountriesAPIRestConsumingWebServices.model;

import localhost.ws.Moneda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data // toString, equals, hashcode, getters, setters
@NoArgsConstructor
@AllArgsConstructor
public class CountryDTO { // DTO: data transfer object
    private String name;
    private String capital;
    private Moneda currency;
    private Integer populationDensity;
    private String flag;
    private Map<String, String> languages;
    private Map<String, String> maps; // por ejemplo, Google Maps

}
