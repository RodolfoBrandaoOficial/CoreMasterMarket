package com.rodolfobrandao.coremastermarket.tools.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OpenApiConfigTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    @org.junit.jupiter.api.Test
    public void testOpenApiConfig() {
        String url = "http://localhost:" + port + "/v3/api-docs";
        String response = restTemplate.getForObject(url, String.class);

        assertNotNull(response, "Response should not be null");
        assertTrue(response.contains("CoreMasterMarket API"), "Response should contain 'CoreMasterMarket API'");
    }
}
