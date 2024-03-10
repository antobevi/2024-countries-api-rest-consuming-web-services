package com.antobevi.CountriesAPIRestConsumingWebServices;

import com.antobevi.CountriesAPIRestConsumingWebServices.controllers.CountryController;
import com.antobevi.CountriesAPIRestConsumingWebServices.model.CountryDTO;
import com.antobevi.CountriesAPIRestConsumingWebServices.model.apiresponse.CountryData;
import com.antobevi.CountriesAPIRestConsumingWebServices.webservices.apirest.CountryRestService;
import com.antobevi.CountriesAPIRestConsumingWebServices.webservices.soap.CountrySoapClient;
import localhost.ws.Moneda;
import localhost.ws.ObtenerPaisResponse;
import localhost.ws.Pais;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = CountriesApiRestConsumingWebServicesApplication.class) // como se hace una emulacion por encima (mock), es importante declarar cual es la clase main
public class CountryControllerTest {
    // primero especificamos que vamos a mockear
    @Mock
    private CountrySoapClient countrySoapClient;
    @Mock
    private CountryRestService countryRestService;
    // luego especificamos en donde inyectamos ese mock (a que clase le pasamos los datos hardcodeados)
    @InjectMocks
    private CountryController countryController;
    private static final String COUNTRY_NAME = "Argentina";

    // Builders
    private Pais buildCountryMockSOAP() {
        Pais soapCountry = new Pais();

        soapCountry.setNombre(COUNTRY_NAME);
        soapCountry.setCapital("Ciudad Autónoma de Buenos Aires");
        soapCountry.setMoneda(Moneda.ARS); // enum
        soapCountry.setPoblacion(46234830);
        soapCountry.setBandera("https://flagcdn.com/w2560/ar.png");

        return soapCountry;
    }

    private CountryData buildCountryMockAPIRest() {
        CountryData apiRestCountry = new CountryData();
        Map<String, String> languages = new HashMap<>();
        Map<String, String> maps = new HashMap<>();

        languages.put("spa", "Spanish");
        maps.put("GoogleMaps", "https://goo.gl/maps/Z9DXNxhf2o93kvyc6");

        apiRestCountry.setLanguages(languages);
        apiRestCountry.setMaps(maps);

        return apiRestCountry;
    }

    // Escenario donde solo funciona el SOAP y la API esta "caida"
    @Test
    void testGetAvailableSOAPCountry() {
        ObtenerPaisResponse soapResponse = new ObtenerPaisResponse();
        Pais countrySOAPAux = buildCountryMockSOAP();

        soapResponse.setPais(countrySOAPAux);

        when(countrySoapClient.getCountry(any())).thenReturn(soapResponse); // cuando llamamos a este metodo con cualquier argumento, debera retornar los datos mockeados

        CountryDTO result = countryController.getCountry(COUNTRY_NAME); // API Rest

        verify(countrySoapClient, times(1)).getCountry(COUNTRY_NAME);

        assertAll(
                () -> Assertions.assertEquals(countrySOAPAux.getNombre(), result.getName()),
                () -> Assertions.assertEquals(countrySOAPAux.getCapital(), result.getCapital()),
                () -> Assertions.assertEquals(countrySOAPAux.getMoneda(), result.getCurrency()),
                () -> Assertions.assertEquals(countrySOAPAux.getPoblacion(), result.getPopulationDensity()),
                () -> Assertions.assertEquals(countrySOAPAux.getBandera(), result.getFlag()),
                () -> Assertions.assertNull(result.getLanguages()),
                () -> Assertions.assertNull(result.getMaps())
        );
    }

    // Escenario donde solo funciona la API Resto y el SOAP esta "caido"
    @Test
    void testGetAvailableAPIRestCountry() {
        CountryData countryAPIRestAux = buildCountryMockAPIRest(); // objeto para enviar datos
        CountryData countryAPIRestResponse = new CountryData(); // objeto respuesta

        countryAPIRestResponse.setLanguages(countryAPIRestAux.getLanguages());
        countryAPIRestResponse.setMaps(countryAPIRestAux.getMaps());

        when(countryRestService.getCountryInfoAPI(any())).thenReturn(countryAPIRestResponse);

        CountryDTO result = countryController.getCountry(COUNTRY_NAME);

        verify(countryRestService, times(1)).getCountryInfoAPI(COUNTRY_NAME);

        assertAll(
                () -> Assertions.assertEquals(countryAPIRestAux.getLanguages(), result.getLanguages()),
                () -> Assertions.assertEquals(countryAPIRestAux.getMaps(), result.getMaps()),
                () -> Assertions.assertNull(result.getName()),
                () -> Assertions.assertNull(result.getCapital()),
                () -> Assertions.assertNull(result.getCurrency()),
                () -> Assertions.assertNull(result.getFlag()),
                () -> Assertions.assertNull(result.getPopulationDensity())
        );
    }

    // Mockeamos como si el soap y la api rest estuvieran caidos para verificar que me traiga valores nulos
    // Escenario donde ambos servicios estan "caidos"
    @Test
    void testGetCountryWhenSOAPAndAPIRestGoDown() {

        when(countrySoapClient.getCountry(any())).thenReturn(null);
        when(countryRestService.getCountryInfoAPI(any())).thenReturn(null);

        CountryDTO result = countryController.getCountry(COUNTRY_NAME);

        verify(countrySoapClient, times(1)).getCountry(COUNTRY_NAME);
        verify(countryRestService, times(1)).getCountryInfoAPI(COUNTRY_NAME);

        assertAll(
                () -> Assertions.assertNull(result.getName()),
                () -> Assertions.assertNull(result.getCapital()),
                () -> Assertions.assertNull(result.getCurrency()),
                () -> Assertions.assertNull(result.getFlag()),
                () -> Assertions.assertNull(result.getPopulationDensity()),
                () -> Assertions.assertNull(result.getLanguages()),
                () -> Assertions.assertNull(result.getMaps())
        );
    }

}