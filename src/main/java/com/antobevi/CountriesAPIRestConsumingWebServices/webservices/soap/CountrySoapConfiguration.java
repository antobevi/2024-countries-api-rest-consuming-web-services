package com.antobevi.CountriesAPIRestConsumingWebServices.webservices.soap;

import com.antobevi.CountriesAPIRestConsumingWebServices.webservices.soap.CountrySoapClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CountrySoapConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

        // Es importante que se llame igual que el PackageName del POM
        marshaller.setContextPath("localhost.ws");

        return marshaller;
    }

    @Bean
    public CountrySoapClient countrySoapClient(Jaxb2Marshaller marshaller) {
        CountrySoapClient client = new CountrySoapClient();

        client.setDefaultUri("http://localhost:8081/ws/");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller); // aca definimos la entidad que va a "transcribir" de java a una sentencia soap y de soap a codigo java

        return client;
    }

}
