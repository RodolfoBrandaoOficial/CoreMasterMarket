package com.rodolfobrandao.coremastermarket.repositories.tools;

import com.rodolfobrandao.coremastermarket.entities.pdv.Venda;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenericQueryServiceConfig {

    @Bean
    public GenericQueryService<Venda> vendaQueryService() {
        return new GenericQueryServiceImpl<>(Venda.class);
    }
}
