package com.rodolfobrandao.coremastermarket.tools;

import com.rodolfobrandao.coremastermarket.entities.Cliente;
import com.rodolfobrandao.coremastermarket.repositories.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void testSaveAndFindCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Teste");
        cliente.setDataNascimento(LocalDate.of(1990, 1, 1));
        cliente.setRua("Rua Teste");
        cliente.setBairro("Bairro Teste");
        cliente.setNumero("123");
        cliente.setCpfCnpj("12345678901");
        cliente.setRgIe("123456789");
        cliente.setTelefone1("123456789");
        cliente.setTelefone2("987654321");
        cliente.setEmitirNota(true);
        cliente.setEmail("cliente@teste.com");
        cliente.setCep("12345-678");
        cliente.setCidade("Cidade Teste");
        cliente.setEstado("Estado Teste");
        cliente.setDataCadastro(LocalDateTime.now());
        cliente.setDataAlteracao(LocalDateTime.now());
        cliente.setAtivo(true);
        cliente.setLimiteCredito(1000.0);
        cliente.setDataPagamento(LocalDate.now());
        cliente.setDataVencimento(LocalDate.now().plusDays(30));
        cliente.setDataFechamentoFatura(LocalDate.now().plusDays(15));

        Cliente savedCliente = clienteRepository.save(cliente);

        assertNotNull(savedCliente.getId());
        assertEquals(cliente.getNome(), savedCliente.getNome());
    }
}
