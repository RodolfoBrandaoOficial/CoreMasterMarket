package com.rodolfobrandao.coremastermarket.services.pve;

import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateMetadosPagamentosDTO;
import com.rodolfobrandao.coremastermarket.entities.pdv.MetadosPagamentos;
import com.rodolfobrandao.coremastermarket.repositories.pdv.MetadosPagamentosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes para a classe {@link MetadosPagamentoService}.
 * <p>
 * Este conjunto de testes utiliza JUnit e Mockito para verificar o comportamento da classe {@link MetadosPagamentoService}.
 * Cada método de teste verifica uma funcionalidade específica do serviço, como criar, atualizar, excluir e buscar
 * entidades {@link MetadosPagamentos}.
 */
@ExtendWith(MockitoExtension.class)
public class MetadosPagamentoServiceTest {

    @Mock
    private MetadosPagamentosRepository metadosPagamentosRepository;

    @InjectMocks
    private MetadosPagamentoService metadosPagamentoService;

    private MetadosPagamentos mockMetadosPagamentos;
    private CreateMetadosPagamentosDTO mockDTO;

    /**
     * Configura os objetos mock antes da execução de cada teste.
     * <p>
     * Este método é executado antes de cada teste e inicializa os objetos {@link MetadosPagamentos} e
     * {@link CreateMetadosPagamentosDTO} com valores padrão para uso nos testes.
     */
    @BeforeEach
    void setUp() {
        mockMetadosPagamentos = new MetadosPagamentos();
        mockMetadosPagamentos.setNome("Pagamento Teste");

        mockDTO = new CreateMetadosPagamentosDTO("Pagamento Teste");
    }

    /**
     * Testa a criação de um novo {@link MetadosPagamentos}.
     * <p>
     * Verifica se o método {@link MetadosPagamentoService#create(CreateMetadosPagamentosDTO)} cria e retorna
     * um novo {@link MetadosPagamentos} com base no DTO fornecido. Utiliza Mockito para simular a persistência
     * e garantir que o método  é chamado corretamente.
     */
    @Test
    void testCreate() {
        when(metadosPagamentosRepository.save(any(MetadosPagamentos.class))).thenReturn(mockMetadosPagamentos);

        MetadosPagamentos createdMetadosPagamentos = metadosPagamentoService.create(mockDTO);
        assertNotNull(createdMetadosPagamentos);
        assertEquals(mockDTO.nome(), createdMetadosPagamentos.getNome());
    }

    /**
     * Testa a atualização de um {@link MetadosPagamentos}.
     * <p>
     * Verifica se o método {@link MetadosPagamentoService#update(CreateMetadosPagamentosDTO)} atualiza e retorna
     * um {@link MetadosPagamentos} existente com base no DTO fornecido. Utiliza Mockito para simular a persistência
     * e garantir que o método  é chamado corretamente.
     */
    @Test
    void testUpdate() {
        when(metadosPagamentosRepository.save(any(MetadosPagamentos.class))).thenReturn(mockMetadosPagamentos);

        MetadosPagamentos updatedMetadosPagamentos = metadosPagamentoService.update(mockDTO);
        assertNotNull(updatedMetadosPagamentos);
        assertEquals(mockDTO.nome(), updatedMetadosPagamentos.getNome());
    }

    /**
     * Testa a exclusão de um {@link MetadosPagamentos} pelo ID.
     * <p>
     * Verifica se o método {@link MetadosPagamentoService#deleteById(Long)} exclui um {@link MetadosPagamentos}
     * existente e retorna a entidade excluída. Utiliza Mockito para simular a busca e a exclusão e garante que
     * o método  é chamado corretamente.
     */
    @Test
    void testDeleteById() {
        when(metadosPagamentosRepository.findById(1L)).thenReturn(Optional.of(mockMetadosPagamentos));
        doNothing().when(metadosPagamentosRepository).delete(any(MetadosPagamentos.class));

        MetadosPagamentos deletedMetadosPagamentos = metadosPagamentoService.deleteById(1L);
        assertNotNull(deletedMetadosPagamentos);
        assertEquals(mockMetadosPagamentos.getNome(), deletedMetadosPagamentos.getNome());
    }

    /**
     * Testa a exclusão de um {@link MetadosPagamentos} quando o ID não é encontrado.
     * <p>
     * Verifica o comportamento do método {@link MetadosPagamentoService#deleteById(Long)} quando a entidade com o
     * ID fornecido não é encontrada. O método deve retornar um {@link MetadosPagamentos} com valores nulos ou
     * padrões.
     */
    @Test
    void testDeleteByIdNotFound() {
        when(metadosPagamentosRepository.findById(1L)).thenReturn(Optional.empty());

        MetadosPagamentos deletedMetadosPagamentos = metadosPagamentoService.deleteById(1L);
        assertNotNull(deletedMetadosPagamentos);
        assertNull(deletedMetadosPagamentos.getNome());
    }

    /**
     * Testa a recuperação de todos os {@link MetadosPagamentos}.
     * <p>
     * Verifica se o método {@link MetadosPagamentoService#findAll()} retorna uma coleção de {@link MetadosPagamentos}
     * quando existem registros no repositório. Utiliza Mockito para simular a recuperação de dados e garante que
     * a coleção retornada contém o mock esperado.
     */
    @Test
    void testFindAll() {
        when(metadosPagamentosRepository.findAll()).thenReturn(Collections.singletonList(mockMetadosPagamentos));

        Collection<MetadosPagamentos> metadosPagamentos = metadosPagamentoService.findAll();
        assertNotNull(metadosPagamentos);
        assertEquals(1, metadosPagamentos.size());
        assertTrue(metadosPagamentos.contains(mockMetadosPagamentos));
    }

    /**
     * Testa a recuperação de todos os {@link MetadosPagamentos} quando não há registros.
     * <p>
     * Verifica se o método {@link MetadosPagamentoService#findAll()} retorna uma coleção vazia quando não há
     * registros no repositório. Utiliza Mockito para simular uma resposta vazia do repositório e garante que a
     * coleção retornada esteja vazia.
     */
    @Test
    void testFindAllEmpty() {
        when(metadosPagamentosRepository.findAll()).thenReturn(Collections.emptyList());

        Collection<MetadosPagamentos> metadosPagamentos = metadosPagamentoService.findAll();
        assertNotNull(metadosPagamentos);
        assertTrue(metadosPagamentos.isEmpty());
    }
}
