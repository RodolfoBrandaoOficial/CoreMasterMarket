package com.rodolfobrandao.coremastermarket.controllers.pdv;

import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateModoPagamentoDTO;
import com.rodolfobrandao.coremastermarket.dtos.pdv.UpdateModoPagamentoDTO;
import com.rodolfobrandao.coremastermarket.entities.pdv.MetadosPagamentos;
import com.rodolfobrandao.coremastermarket.entities.pdv.ModoPagamento;
import com.rodolfobrandao.coremastermarket.services.pve.ModoPagamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Testes para a classe ModoPagamentoController.
 *
 * Esta classe contém testes unitários para os endpoints do controlador ModoPagamentoController,
 * utilizando Mockito para simular o comportamento do serviço ModoPagamentoService.
 */
class ModoPagamentoControllerTest {

    @Mock
    private ModoPagamentoService modoPagamentoService;

    @InjectMocks
    private ModoPagamentoController modoPagamentoController;

    private ModoPagamento modoPagamento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MetadosPagamentos tipoPagamento = new MetadosPagamentos(1L, "Crédito");
        modoPagamento = new ModoPagamento(1L, BigDecimal.valueOf(1000), BigDecimal.valueOf(500), BigDecimal.valueOf(2.5), 12, LocalDateTime.now(), tipoPagamento, "Descrição adicional", Collections.emptyList());
    }

    /**
     * Testa o endpoint para buscar um ModoPagamento por ID.
     *
     * Verifica se o método getModoPagamentoById retorna o ModoPagamento correto e o status HTTP 200 OK quando o objeto é encontrado.
     */
    @Test
    void testGetModoPagamentoById() {
        when(modoPagamentoService.findById(anyLong())).thenReturn(Optional.of(modoPagamento));

        ResponseEntity<ModoPagamento> response = modoPagamentoController.getModoPagamentoById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status HTTP deve ser 200 OK.");
        assertEquals(modoPagamento, response.getBody(), "ModoPagamento retornado deve ser igual ao esperado.");
    }

    /**
     * Testa o endpoint para buscar um ModoPagamento por ID quando o objeto não é encontrado.
     *
     * Verifica se o método getModoPagamentoById retorna o status HTTP 404 NOT FOUND quando o objeto não é encontrado.
     */
    @Test
    void testGetModoPagamentoByIdNotFound() {
        when(modoPagamentoService.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<ModoPagamento> response = modoPagamentoController.getModoPagamentoById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Status HTTP deve ser 404 NOT FOUND.");
    }

    /**
     * Testa o endpoint para buscar todos os ModoPagamentos.
     *
     * Verifica se o método getAllModoPagamentos retorna a lista de ModoPagamentos e o status HTTP 200 OK.
     */
    @Test
    void testGetAllModoPagamentos() {
        when(modoPagamentoService.findAll()).thenReturn(Collections.singletonList(modoPagamento));

        ResponseEntity<List<ModoPagamento>> response = modoPagamentoController.getAllModoPagamentos();

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status HTTP deve ser 200 OK.");
        assertEquals(1, response.getBody().size(), "Lista deve conter um item.");
        assertEquals(modoPagamento, response.getBody().get(0), "ModoPagamento retornado deve ser igual ao esperado.");
    }

    /**
     * Testa o endpoint para criar um novo ModoPagamento.
     *
     * Verifica se o método createModoPagamento retorna o ModoPagamento criado e o status HTTP 201 CREATED.
     */
    @Test
    void testCreateModoPagamento() {
        CreateModoPagamentoDTO dto = new CreateModoPagamentoDTO(BigDecimal.valueOf(1000), BigDecimal.valueOf(500), BigDecimal.valueOf(2.5), 12, LocalDateTime.now(), List.of(1L), "Descrição adicional");

        when(modoPagamentoService.create(any(CreateModoPagamentoDTO.class))).thenReturn(modoPagamento);

        ResponseEntity<ModoPagamento> response = modoPagamentoController.createModoPagamento(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status HTTP deve ser 201 CREATED.");
        assertEquals(modoPagamento, response.getBody(), "ModoPagamento retornado deve ser igual ao esperado.");
    }

    /**
     * Testa o endpoint para atualizar um ModoPagamento existente.
     *
     * Verifica se o método updateModoPagamento retorna o ModoPagamento atualizado e o status HTTP 200 OK.
     */
    @Test
    void testUpdateModoPagamento() {
        UpdateModoPagamentoDTO dto = new UpdateModoPagamentoDTO(1L, BigDecimal.valueOf(1500), BigDecimal.valueOf(750), BigDecimal.valueOf(1.5), 10, LocalDateTime.now(), List.of(1L, 2L), "Descrição atualizada");

        when(modoPagamentoService.update(anyLong(), any(UpdateModoPagamentoDTO.class))).thenReturn(modoPagamento);

        ResponseEntity<ModoPagamento> response = modoPagamentoController.updateModoPagamento(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status HTTP deve ser 200 OK.");
        assertEquals(modoPagamento, response.getBody(), "ModoPagamento retornado deve ser igual ao esperado.");
    }

    /**
     * Testa o endpoint para excluir um ModoPagamento por ID.
     *
     * Verifica se o método deleteModoPagamento retorna o status HTTP 204 NO CONTENT e se o método deleteById do serviço é chamado.
     */
    @Test
    void testDeleteModoPagamento() {
        doNothing().when(modoPagamentoService).deleteById(anyLong());

        ResponseEntity<Void> response = modoPagamentoController.deleteModoPagamento(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode(), "Status HTTP deve ser 204 NO CONTENT.");
        verify(modoPagamentoService, times(1)).deleteById(1L);
    }
}
