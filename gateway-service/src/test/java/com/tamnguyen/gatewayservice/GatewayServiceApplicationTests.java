package com.tamnguyen.gatewayservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GatewayServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void contextLoads() {

        webTestClient
                .get().uri("/api/v1/welcome/")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Welcome to booking system...");
    }
}
