package com.rodolfobrandao.coremastermarket.services.cliente;

import com.rodolfobrandao.coremastermarket.dtos.cliente.CreateClienteItemDTO;
import com.rodolfobrandao.coremastermarket.dtos.cliente.UpdateClienteItemDTO;
import com.rodolfobrandao.coremastermarket.entities.cliente.Cliente;
import com.rodolfobrandao.coremastermarket.repositories.cliente.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa a busca de todos os clientes com paginação.
     *
     * Este teste configura o mock para retornar uma página de clientes e verifica se o método `findAll` retorna a página esperada.
     */
    @Test
    void testFindAll() {
        // Prepare mock data
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        Page<Cliente> clientesPage = new PageImpl<>(Collections.singletonList(cliente));

        when(clienteRepository.findAll(any(Pageable.class))).thenReturn(clientesPage);

        // Execute the method and verify the response
        Page<Cliente> result = clienteService.findAll(1, "=", 10, "nome", "asc");

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Cliente Teste", result.getContent().get(0).getNome());
    }

    /**
     * Testa a busca de clientes com base na consulta e filtros fornecidos.
     *
     * Este teste configura o mock para retornar uma página de clientes filtrados e verifica se o método `findByQuery` retorna a página esperada.
     */
    @Test
    void testFindByQuery() {
        // Prepare mock data
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        Page<Cliente> clientesPage = new PageImpl<>(Collections.singletonList(cliente));

        when(clienteRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(clientesPage);

        // Execute the method and verify the response
        Page<Cliente> result = clienteService.findByQuery("nome", "Cliente Teste", "=", 1, 10, "nome", "asc");

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Cliente Teste", result.getContent().get(0).getNome());
    }

    /**
     * Testa a criação de um novo cliente.
     *
     * Este teste configura o mock para salvar um cliente e verifica se o método `create` retorna o cliente criado.
     */
    @Test
    void testCreate() {
        // Prepare mock data
        CreateClienteItemDTO createClienteDTO = new CreateClienteItemDTO(
                "Cliente Teste", LocalDate.now(), "Rua Teste", "Bairro Teste", "123",
                "12345678900", "1234567", "123456789", "987654321", true, "email@test.com",
                "12345-678", "Cidade Teste", "Estado Teste", LocalDateTime.now(),
                LocalDateTime.now().toLocalDate(), LocalDateTime.now().toLocalDate(), true,
                "Observação Teste", 1000.00, LocalDate.now(), LocalDate.now(), LocalDate.now()
        );

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Execute the method and verify the response
        Cliente result = clienteService.create(createClienteDTO);

        assertNotNull(result);
        assertEquals("Cliente Teste", result.getNome());
    }

    /**
     * Testa a atualização de um cliente existente.
     *
     * Este teste configura o mock para atualizar um cliente e verifica se o método `update` retorna o cliente atualizado.
     */
    @Test
    void testUpdate() {
        // Prepare mock data
        UpdateClienteItemDTO updateClienteDTO = new UpdateClienteItemDTO(
                1L, "Cliente Atualizado", LocalDate.now(), "Rua Atualizada", "Bairro Atualizado", "456",
                "12345678901", "7654321", "123456788", "987654320", true, "email_atualizado@test.com",
                "54321-678", "Cidade Atualizada", "Estado Atualizado", LocalDateTime.now(),
                LocalDateTime.now().toLocalDate(), LocalDateTime.now().toLocalDate(), true,
                "Observação Atualizada", 2000.00, LocalDate.now(), LocalDate.now(), LocalDate.now()
        );

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Atualizado");

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Execute the method and verify the response
        Cliente result = clienteService.update(updateClienteDTO);

        assertNotNull(result);
        assertEquals("Cliente Atualizado", result.getNome());
    }

    /**
     * Testa a exclusão de um cliente.
     *
     * Este teste configura o mock para deletar um cliente e verifica se o método `delete` executa a exclusão sem erros.
     */
    @Test
    void testDelete() {
        // Execute the method and verify the response
        clienteService.delete(1L);

        // Verify that deleteById was called with the correct ID
        verify(clienteRepository, times(1)).deleteById(1L);
    }
}
