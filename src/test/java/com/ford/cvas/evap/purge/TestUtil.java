package com.ford.cvas.evap.purge;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class TestUtil {

    private TestUtil() {
        super();
    }

    public static ClientResponse post(WebClient webClient, String urlPath, Object requestObj) {
        return webClient.post()
                .uri(urlPath)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(requestObj))
                .exchangeToMono(Mono::just)
                .timeout(Duration.ofMillis(30000))
                .block();
    }

    public static ClientResponse get(WebClient webClient, String urlPath) {
        return webClient.get().uri(urlPath).exchangeToMono(Mono::just).timeout(Duration.ofMillis(30000)).block();
    }

    public static List<String> getSensitiveActuatorEndpoints() {
        List<String> endpoints = new ArrayList<>();
        endpoints.add("/actuator/actuator");
        endpoints.add("/actuator/auditevents");
        endpoints.add("/actuator/autoconfig");
        endpoints.add("/actuator/beans");
        endpoints.add("/actuator/configprops");
        endpoints.add("/actuator/dump");
        endpoints.add("/actuator/env");
        //endpoints.add("/actuator/flyway");
        endpoints.add("/actuator/loggers");
        endpoints.add("/actuator/liquibase");
        endpoints.add("/actuator/metrics");
        endpoints.add("/actuator/mappings");
        endpoints.add("/actuator/shutdown");
        endpoints.add("/actuator/trace");
        return endpoints;
    }
}