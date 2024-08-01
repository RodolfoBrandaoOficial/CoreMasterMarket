package com.rodolfobrandao.coremastermarket.services.produto;

import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateMarcaDTO;
import com.rodolfobrandao.coremastermarket.entities.produto.Marca;
import com.rodolfobrandao.coremastermarket.repositories.produto.MarcaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MarcaServiceTest {

    @Mock
    private MarcaRepository marcaRepository;

    @InjectMocks
    private MarcaService marcaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa a busca de todas as marcas com paginação.
     *
     * Este teste configura o mock para retornar uma página de marcas e verifica se o método `findAll` retorna a página esperada.
     */
    @Test
    void testFindAll() {
        // Prepare mock data
        Marca marca = new Marca();
        marca.setId(1L);
        marca.setNome("Marca Teste");

        Page<Marca> marcasPage = new PageImpl<>(Collections.singletonList(marca));

        when(marcaRepository.findAll(any(PageRequest.class))).thenReturn(marcasPage);

        // Execute the method and verify the response
        Page<Marca> result = marcaService.findAll(1, 10, "nome", "asc");

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Marca Teste", result.getContent().get(0).getNome());
    }

    /**
     * Testa a criação de uma nova marca.
     *
     * Este teste configura o mock para salvar uma marca e verifica se o método `create` retorna a marca esperada.
     */
    @Test
    void testCreate() {
        // Prepare mock data
        CreateMarcaDTO createMarcaDTO = new CreateMarcaDTO("Marca Teste", "Fabricante Teste");

        Marca marca = new Marca();
        marca.setId(1L);
        marca.setNome("Marca Teste");
        marca.setFabricante("Fabricante Teste");

        when(marcaRepository.save(any(Marca.class))).thenReturn(marca);

        // Execute the method and verify the response
        Marca result = marcaService.create(createMarcaDTO);

        assertNotNull(result);
        assertEquals("Marca Teste", result.getNome());
        assertEquals("Fabricante Teste", result.getFabricante());
    }

    /**
     * Testa a busca de uma marca por ID.
     *
     * Este teste configura o mock para retornar uma marca e verifica se o método `findById` retorna a marca esperada.
     */
    @Test
    void testFindById() {
        // Prepare mock data
        Marca marca = new Marca();
        marca.setId(1L);
        marca.setNome("Marca Teste");

        when(marcaRepository.findById(anyLong())).thenReturn(Optional.of(marca));

        // Execute the method and verify the response
        Marca result = marcaService.findById(1L);

        assertNotNull(result);
        assertEquals("Marca Teste", result.getNome());
    }

    /**
     * Testa a exclusão de uma marca por ID.
     *
     * Este teste configura o mock para retornar e deletar uma marca e verifica se o método `delete` retorna a marca esperada.
     */
    @Test
    void testDelete() {
        // Prepare mock data
        Marca marca = new Marca();
        marca.setId(1L);
        marca.setNome("Marca Teste");

        when(marcaRepository.findById(anyLong())).thenReturn(Optional.of(marca));
        doNothing().when(marcaRepository).delete(any(Marca.class));

        // Execute the method and verify the response
        Marca result = marcaService.delete(1L);

        assertNotNull(result);
        assertEquals("Marca Teste", result.getNome());
        verify(marcaRepository, times(1)).delete(marca);
    }
}
