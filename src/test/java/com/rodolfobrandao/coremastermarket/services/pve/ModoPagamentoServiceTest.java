package com.rodolfobrandao.coremastermarket.services.pve;

import com.rodolfobrandao.coremastermarket.entities.pdv.MetadosPagamentos;
import com.rodolfobrandao.coremastermarket.entities.pdv.ModoPagamento;
import com.rodolfobrandao.coremastermarket.repositories.pdv.MetadosPagamentosRepository;
import com.rodolfobrandao.coremastermarket.repositories.pdv.ModoPagamentoRepository;
import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateModoPagamentoDTO;
import com.rodolfobrandao.coremastermarket.dtos.pdv.UpdateModoPagamentoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes para a classe ModoPagamentoService.
 *
 * Esta classe contém testes unitários para as operações CRUD do serviço ModoPagamentoService,
 * utilizando Mockito para simular o comportamento das dependências.
 */
public class ModoPagamentoServiceTest {

    @Mock
    private ModoPagamentoRepository modoPagamentoRepository;

    @Mock
    private MetadosPagamentosRepository metadosPagamentosRepository;

    @InjectMocks
    private ModoPagamentoService modoPagamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa a busca de um ModoPagamento por ID.
     *
     * Verifica se o método findById retorna o ModoPagamento correto quando existe um objeto com o ID fornecido.
     */
    @Test
    void testFindById() {
        Long id = 1L;
        ModoPagamento modoPagamento = new ModoPagamento();
        when(modoPagamentoRepository.findById(id)).thenReturn(Optional.of(modoPagamento));

        Optional<ModoPagamento> result = modoPagamentoService.findById(id);
        assertTrue(result.isPresent(), "ModoPagamento deve estar presente.");
        assertEquals(modoPagamento, result.get(), "ModoPagamento retornado deve ser igual ao esperado.");
    }

    /**
     * Testa a busca de todos os ModoPagamentos.
     *
     * Verifica se o método findAll retorna a lista correta de ModoPagamentos.
     */
    @Test
    void testFindAll() {
        List<ModoPagamento> modoPagamentos = Arrays.asList(new ModoPagamento(), new ModoPagamento());
        when(modoPagamentoRepository.findAll()).thenReturn(modoPagamentos);

        List<ModoPagamento> result = modoPagamentoService.findAll();
        assertEquals(modoPagamentos, result, "Lista de ModoPagamentos retornada deve ser igual à lista esperada.");
    }

    /**
     * Testa a criação e salvamento de um novo ModoPagamento.
     *
     * Verifica se o método save retorna o ModoPagamento correto e se o ModoPagamento é salvo conforme esperado.
     */
    @Test
    void testSave() {
        ModoPagamento modoPagamento = new ModoPagamento();
        when(modoPagamentoRepository.save(modoPagamento)).thenReturn(modoPagamento);

        ModoPagamento result = modoPagamentoService.save(modoPagamento);
        assertEquals(modoPagamento, result, "ModoPagamento retornado deve ser igual ao esperado.");
    }

    /**
     * Testa a criação de um ModoPagamento com base no DTO fornecido.
     *
     * Verifica se o método create configura corretamente o ModoPagamento e o retorna após o salvamento.
     */
    @Test
    void testCreate() {
        // Arrange
        CreateModoPagamentoDTO dto = new CreateModoPagamentoDTO(
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(500),
                BigDecimal.valueOf(5),
                10,
                LocalDateTime.now(),
                Arrays.asList(1L),
                "Descricao"
        );

        // Mock do MetadosPagamentos
        MetadosPagamentos tipoPagamento = new MetadosPagamentos();
        tipoPagamento.setId(1L);
        when(metadosPagamentosRepository.findAllById(dto.tiposPagamento())).thenReturn(Arrays.asList(tipoPagamento));

        // Mock do ModoPagamentoRepository
        ModoPagamento modoPagamento = new ModoPagamento();
        modoPagamento.setLimite(dto.limite());
        modoPagamento.setSaldo(dto.saldo());
        modoPagamento.setJuros(dto.juros());
        modoPagamento.setParcelas(dto.parcelas());
        modoPagamento.setDataGerada(dto.dataGerada());
        modoPagamento.setTipoPagamento(tipoPagamento);
        modoPagamento.setDescricao(dto.descricao());

        when(modoPagamentoRepository.save(any(ModoPagamento.class))).thenReturn(modoPagamento);

        // Act
        ModoPagamento result = modoPagamentoService.create(dto);

        // Assert
        assertNotNull(result, "ModoPagamento retornado não pode ser null.");
        assertEquals(dto.limite(), result.getLimite(), "Limite deve ser igual ao esperado.");
        assertEquals(dto.saldo(), result.getSaldo(), "Saldo deve ser igual ao esperado.");
        assertEquals(dto.juros(), result.getJuros(), "Juros deve ser igual ao esperado.");
        assertEquals(dto.parcelas(), result.getParcelas(), "Parcelas devem ser iguais ao esperado.");
        assertEquals(dto.dataGerada(), result.getDataGerada(), "Data Gerada deve ser igual à esperada.");
        assertEquals(dto.descricao(), result.getDescricao(), "Descrição deve ser igual à esperada.");
        assertEquals(tipoPagamento, result.getTipoPagamento(), "Tipo de Pagamento deve ser igual ao esperado.");
    }

    /**
     * Testa a criação de um ModoPagamento quando nenhum tipo de pagamento é encontrado.
     *
     * Verifica se uma exceção é lançada quando a lista de tipos de pagamento retornada é vazia.
     */
    @Test
    void testCreateThrowsExceptionWhenNoTiposPagamentoFound() {
        CreateModoPagamentoDTO dto = new CreateModoPagamentoDTO(
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(500),
                BigDecimal.valueOf(5),
                10,
                LocalDateTime.now(),
                Arrays.asList(1L),
                "Descricao"
        );
        when(metadosPagamentosRepository.findAllById(dto.tiposPagamento())).thenReturn(Arrays.asList());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            modoPagamentoService.create(dto);
        });

        assertEquals("Nenhum tipo de pagamento encontrado para os IDs fornecidos.", exception.getMessage(), "Mensagem de exceção deve ser igual à esperada.");
    }

    /**
     * Testa a atualização de um ModoPagamento com base no DTO fornecido.
     *
     * Verifica se o método update configura corretamente o ModoPagamento e o retorna após a atualização.
     */
    @Test
    void testUpdate() {
        Long id = 1L;
        UpdateModoPagamentoDTO dto = new UpdateModoPagamentoDTO(
                id,
                BigDecimal.valueOf(2000),
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(10),
                15,
                LocalDateTime.now(),
                Arrays.asList(2L),
                "Nova Descricao"
        );
        ModoPagamento existingModoPagamento = new ModoPagamento();
        existingModoPagamento.setTipoPagamento(new MetadosPagamentos());

        MetadosPagamentos tipoPagamento = new MetadosPagamentos();
        when(modoPagamentoRepository.findById(id)).thenReturn(Optional.of(existingModoPagamento));
        when(metadosPagamentosRepository.findAllById(dto.tipoPagamentoIds())).thenReturn(Arrays.asList(tipoPagamento));
        when(modoPagamentoRepository.save(existingModoPagamento)).thenReturn(existingModoPagamento);

        ModoPagamento result = modoPagamentoService.update(id, dto);
        assertEquals(dto.limite(), result.getLimite(), "Limite deve ser igual ao esperado.");
        assertEquals(dto.saldo(), result.getSaldo(), "Saldo deve ser igual ao esperado.");
        assertEquals(dto.juros(), result.getJuros(), "Juros deve ser igual ao esperado.");
        assertEquals(dto.parcelas(), result.getParcelas(), "Parcelas devem ser iguais ao esperado.");
        assertEquals(dto.dataGerada(), result.getDataGerada(), "Data Gerada deve ser igual à esperada.");
        assertEquals(dto.descricao(), result.getDescricao(), "Descrição deve ser igual à esperada.");
        assertEquals(tipoPagamento, result.getTipoPagamento(), "Tipo de Pagamento deve ser igual ao esperado.");
    }

    /**
     * Testa a atualização de um ModoPagamento quando nenhum tipo de pagamento é encontrado.
     *
     * Verifica se uma exceção é lançada quando a lista de tipos de pagamento retornada é vazia.
     */
    @Test
    void testUpdateThrowsExceptionWhenNoTiposPagamentoFound() {
        Long id = 1L;
        UpdateModoPagamentoDTO dto = new UpdateModoPagamentoDTO(
                id,
                BigDecimal.valueOf(2000),
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(10),
                15,
                LocalDateTime.now(),
                Arrays.asList(2L),
                "Nova Descricao"
        );
        ModoPagamento existingModoPagamento = new ModoPagamento();
        when(modoPagamentoRepository.findById(id)).thenReturn(Optional.of(existingModoPagamento));
        when(metadosPagamentosRepository.findAllById(dto.tipoPagamentoIds())).thenReturn(Arrays.asList());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            modoPagamentoService.update(id, dto);
        });

        assertEquals("Nenhum tipo de pagamento encontrado para os IDs fornecidos.", exception.getMessage(), "Mensagem de exceção deve ser igual à esperada.");
    }

    /**
     * Testa a exclusão de um ModoPagamento por ID.
     *
     * Verifica se o método deleteById chama o método correspondente do repositório.
     */
    @Test
    void testDeleteById() {
        Long id = 1L;
        doNothing().when(modoPagamentoRepository).deleteById(id);

        modoPagamentoService.deleteById(id);

        verify(modoPagamentoRepository, times(1)).deleteById(id);
    }
}
