package com.rodolfobrandao.coremastermarket.controllers.pdv;

import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateMetadosPagamentosDTO;
import com.rodolfobrandao.coremastermarket.dtos.pdv.DeleteMetadosPagamentosDTO;
import com.rodolfobrandao.coremastermarket.entities.pdv.MetadosPagamentos;
import com.rodolfobrandao.coremastermarket.services.pve.MetadosPagamentoService;
import com.rodolfobrandao.coremastermarket.tools.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link MetadosPagamentosController}.
 *
 * This test class contains unit tests for the methods of the {@link MetadosPagamentosController}
 * to ensure that the controller behaves correctly in various scenarios.
 */
class MetadosPagamentosControllerTest {

    @Mock
    private MetadosPagamentoService metadosPagamentoService;

    @InjectMocks
    private MetadosPagamentosController metadosPagamentosController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the {@link MetadosPagamentosController#create(CreateMetadosPagamentosDTO)}
     * method for a successful creation of a MetadosPagamentos.
     */
    @Test
    void testCreate() {
        CreateMetadosPagamentosDTO dto = new CreateMetadosPagamentosDTO("Cartão de Crédito");
        MetadosPagamentos metadosPagamentos = new MetadosPagamentos(1L, "Cartão de Crédito");

        when(metadosPagamentoService.create(any(CreateMetadosPagamentosDTO.class))).thenReturn(metadosPagamentos);

        ResponseEntity<?> response = metadosPagamentosController.create(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(metadosPagamentos, response.getBody());
    }

    /**
     * Tests the {@link MetadosPagamentosController#create(CreateMetadosPagamentosDTO)}
     * method when an error occurs during creation of a MetadosPagamentos.
     */
    @Test
    void testCreateError() {
        CreateMetadosPagamentosDTO dto = new CreateMetadosPagamentosDTO("Cartão de Crédito");

        when(metadosPagamentoService.create(any(CreateMetadosPagamentosDTO.class))).thenThrow(new RuntimeException("Erro ao criar"));

        ResponseEntity<?> response = metadosPagamentosController.create(dto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao criar metado de pagamento: Erro ao criar", response.getBody());
    }

    /**
     * Tests the {@link MetadosPagamentosController#update(CreateMetadosPagamentosDTO)}
     * method for a successful update of a MetadosPagamentos.
     */
    @Test
    void testUpdate() {
        CreateMetadosPagamentosDTO dto = new CreateMetadosPagamentosDTO("Cartão de Crédito");
        MetadosPagamentos metadosPagamentos = new MetadosPagamentos(1L, "Cartão de Crédito");

        when(metadosPagamentoService.update(any(CreateMetadosPagamentosDTO.class))).thenReturn(metadosPagamentos);

        ResponseEntity<?> response = metadosPagamentosController.update(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(metadosPagamentos, response.getBody());
    }

    /**
     * Tests the {@link MetadosPagamentosController#update(CreateMetadosPagamentosDTO)}
     * method when an error occurs during the update of a MetadosPagamentos.
     */
    @Test
    void testUpdateError() {
        CreateMetadosPagamentosDTO dto = new CreateMetadosPagamentosDTO("Cartão de Crédito");

        when(metadosPagamentoService.update(any(CreateMetadosPagamentosDTO.class))).thenThrow(new RuntimeException("Erro ao atualizar"));

        ResponseEntity<?> response = metadosPagamentosController.update(dto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao atualizar metado de pagamento: Erro ao atualizar", response.getBody());
    }

    /**
     * Tests the {@link MetadosPagamentosController#delete(DeleteMetadosPagamentosDTO)}
     * method for a successful deletion of a MetadosPagamentos.
     */
    @Test
    void testDelete() {
        DeleteMetadosPagamentosDTO dto = new DeleteMetadosPagamentosDTO(1L);
        MetadosPagamentos metadosPagamentos = new MetadosPagamentos(1L, "Cartão de Crédito");

        when(metadosPagamentoService.deleteById(any(Long.class))).thenReturn(metadosPagamentos);

        ResponseEntity<?> response = metadosPagamentosController.delete(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(JsonUtil.createMessageJson("Metado de pagamento deletado com sucesso", 200), response.getBody());
    }

    /**
     * Tests the {@link MetadosPagamentosController#delete(DeleteMetadosPagamentosDTO)}
     * method when the MetadosPagamentos to delete is not found.
     */
    @Test
    void testDeleteNotFound() {
        DeleteMetadosPagamentosDTO dto = new DeleteMetadosPagamentosDTO(1L);

        when(metadosPagamentoService.deleteById(any(Long.class))).thenReturn(null);

        ResponseEntity<?> response = metadosPagamentosController.delete(dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(JsonUtil.createMessageJson("Metado de pagamento não encontrado", 404), response.getBody());
    }

    /**
     * Tests the {@link MetadosPagamentosController#delete(DeleteMetadosPagamentosDTO)}
     * method when an error occurs during the deletion of a MetadosPagamentos.
     */
    @Test
    void testDeleteError() {
        DeleteMetadosPagamentosDTO dto = new DeleteMetadosPagamentosDTO(1L);

        when(metadosPagamentoService.deleteById(any(Long.class))).thenThrow(new RuntimeException("Erro ao deletar"));

        ResponseEntity<?> response = metadosPagamentosController.delete(dto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao deletar metado de pagamento: Erro ao deletar", response.getBody());
    }

    /**
     * Tests the {@link MetadosPagamentosController#findAll()}
     * method for successfully retrieving all MetadosPagamentos.
     */
    @Test
    void testFindAll() {
        MetadosPagamentos metadosPagamentos = new MetadosPagamentos(1L, "Cartão de Crédito");

        when(metadosPagamentoService.findAll()).thenReturn(Collections.singletonList(metadosPagamentos));

        ResponseEntity<?> response = metadosPagamentosController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonList(metadosPagamentos), response.getBody());
    }

    /**
     * Tests the {@link MetadosPagamentosController#findAll()}
     * method when an error occurs during retrieval of all MetadosPagamentos.
     */
    @Test
    void testFindAllError() {
        when(metadosPagamentoService.findAll()).thenThrow(new RuntimeException("Erro ao buscar"));

        ResponseEntity<?> response = metadosPagamentosController.findAll();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(JsonUtil.createMessageJson("Erro ao buscar todos os metados de pagamento: Erro ao buscar", 500), response.getBody());
    }
}
