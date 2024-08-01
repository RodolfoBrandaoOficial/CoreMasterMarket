package com.rodolfobrandao.coremastermarket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Classe de configuração para carregar o banco de dados.
 * Implementa CommandLineRunner para executar código após a inicialização do Spring Boot.
 */
@Slf4j
@Configuration
public class PreLoadDatabase implements CommandLineRunner {
    private final DataSource dataSource;
    private final JdbcClient jdbcClient;

    /**
     * Construtor para injeção de dependências.
     *
     * @param dataSource Fonte de dados para conexão com o banco de dados.
     * @param jdbcClient Cliente JDBC para execução de consultas SQL.
     */
    public PreLoadDatabase(DataSource dataSource, JdbcClient jdbcClient) {
        this.dataSource = dataSource;
        this.jdbcClient = jdbcClient;
    }

    /**
     * Método executado após a inicialização do Spring Boot.
     * Verifica se o banco de dados está vazio e, se estiver, popula-o com dados iniciais.
     *
     * @param args Argumentos da linha de comando.
     * @throws Exception Se ocorrer algum erro durante a execução.
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("Verificando se o banco de dados está vazio...");
        Long count = jdbcClient
                .sql("SELECT COUNT(*) FROM users")
                .query(Long.class)
                .single();

        if (count == 0) {
            log.info("Banco de dados vazio. Populando o banco de dados...");
            executarScriptSql();
            log.info("Banco de dados populado com sucesso.");
        } else {
            log.info("Banco de dados possui registros. Não será necessário popular o banco de dados.");
        }
    }

    /**
     * Executa o script SQL para popular o banco de dados.
     */
    private void executarScriptSql() {
        Resource resource = new ClassPathResource("database.sql");
        try {
            EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
            ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao executar o script SQL", e);
        }
    }
}
