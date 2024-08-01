package com.rodolfobrandao.coremastermarket.tools.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CoreMasterMarket API")
                        .version("0.0.1")
                        .description("API para gerenciamento de vendas, clientes e produtos no CoreMasterMarket")
                        .summary("(Teste prático - Desenvolvedor Java VR-SOFTWARE)")
                        .termsOfService("https://github.com/RodolfoBrandaoOficial/CoreMasterMarket")
                        .contact(new Contact()
                                .name("Rodolfo Brandão")
                                .url("https://github.com/RodolfoBrandaoOficial/CoreMasterMarket")
                                .email("rodolfo@rodolfobrandao.com.br"))
                        .license(new License()
                                .name("Licença")
                                .url("https://github.com/RodolfoBrandaoOficial/CoreMasterMarket")))
                .addServersItem(new Server().url("http://localhost:8081")
                        .description("Servidor de desenvolvimento"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Token JWT para autenticação")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }


}
