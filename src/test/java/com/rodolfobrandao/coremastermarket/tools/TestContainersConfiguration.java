package com.rodolfobrandao.coremastermarket;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestContainersConfiguration {

    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.3"))
                    .withDatabaseName("coremastermarket")
                    .withUsername("postgres")
                    .withPassword("senha_vrcursojava");

    static {
        postgreSQLContainer.start();
        Runtime.getRuntime().addShutdownHook(new Thread(postgreSQLContainer::stop));
    }

    @Bean
    public PostgreSQLContainer<?> postgresSQLContainer() {
        return postgreSQLContainer;
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }
}
