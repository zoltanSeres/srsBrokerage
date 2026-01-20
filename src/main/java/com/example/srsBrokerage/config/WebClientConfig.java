package com.example.srsBrokerage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;

import java.util.Map;

@Configuration
public class WebClientConfig {

    @Value("${alpha-vantage.base-url}")
    private String baseUrl;

    @Value("${alpha-vantage.api-key}")
    private String apiKey;

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Map.of("apikey", apiKey))
                .build();
    }
}
