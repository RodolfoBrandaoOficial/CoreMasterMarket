package com.rodolfobrandao.coremastermarket.tools;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Exemplo de API")
                        .version("1.0.0")
                        .description("Descrição da API")
                        .termsOfService("https://example.com/terms")
                        .contact(new Contact()
                                .name("Nome do Contato")
                                .url("https://example.com/contact")
                                .email("contato@example.com"))
                        .license(new License()
                                .name("Licença")
                                .url("https://example.com/license")))
                .addServersItem(new Server().url("http://localhost:8080"));
    }
}
