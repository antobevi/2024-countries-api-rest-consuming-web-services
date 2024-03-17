package com.antobevi.CountriesAPIRestConsumingWebServices.config;

import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true);
        // addAllowedOrigin en lugar de mediante patrones me permite solo un origen
        configuration.addAllowedOriginPattern("*"); // permite todas las url que hagan peticiones a esta API
        configuration.addAllowedHeader("*"); // que datos se pueden enviar mediante la cabecera
        configuration.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", configuration); // el patron es lo que sigue despues de la URL

        return new CorsFilter(source);
    }

}
