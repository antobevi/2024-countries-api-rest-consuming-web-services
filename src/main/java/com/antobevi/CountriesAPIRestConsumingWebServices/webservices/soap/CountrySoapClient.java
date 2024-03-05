package com.antobevi.CountriesAPIRestConsumingWebServices.webservices.soap;

import localhost.ws.ObtenerPaisRequest;
import localhost.ws.ObtenerPaisResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class CountrySoapClient extends WebServiceGatewaySupport {
    private static final Logger log = LoggerFactory.getLogger(CountrySoapClient.class);
    private static final String urlBase = "http://localhost:8081/ws/";

    public ObtenerPaisResponse getCountry(String countryName) {
        ObtenerPaisRequest request = new ObtenerPaisRequest();

        request.setName(countryName);

        log.info("Obteniendo información del pais " + countryName);

        ObtenerPaisResponse response = (ObtenerPaisResponse) getWebServiceTemplate()
                .marshalSendAndReceive(urlBase + "/paises", request,
                        new SoapActionCallback(urlBase + "/obtenerPaisRequest"));

        return response;
    }

}
