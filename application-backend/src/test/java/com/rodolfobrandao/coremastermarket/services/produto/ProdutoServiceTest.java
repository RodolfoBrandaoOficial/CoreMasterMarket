package com.rodolfobrandao.coremastermarket.services.produto;

import com.rodolfobrandao.coremastermarket.dtos.produto.CreateProdutoDTO;
import com.rodolfobrandao.coremastermarket.entities.produto.Marca;
import com.rodolfobrandao.coremastermarket.entities.produto.Produto;
import com.rodolfobrandao.coremastermarket.repositories.produto.MarcaRepository;
import com.rodolfobrandao.coremastermarket.repositories.produto.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private MarcaRepository marcaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduto() {
        CreateProdutoDTO dto = new CreateProdutoDTO("1234567890123", "Produto Teste", BigDecimal.TEN, "Embalagem", 10L, null, null, 1L, true);
        Marca marca = new Marca();
        Produto produto = new Produto();

        when(marcaRepository.findById(dto.marca())).thenReturn(Optional.of(marca));
        when(produtoRepository.findByCodigoBarras(dto.codigoBarras())).thenReturn(Optional.empty());
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto result = produtoService.create(dto);

        assertNotNull(result);
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void testFindById() {
        Long id = 1L;
        Produto produto = new Produto();
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));

        Produto result = produtoService.findById(id);

        assertNotNull(result);
        assertEquals(produto, result);
    }

    @Test
    void testDeleteById() {
        Long id = 1L;
        Produto produto = new Produto();
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto result = produtoService.deleteById(id);

        assertNotNull(result);
        assertFalse(result.isAtivo());
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void testEnabledById() {
        Long id = 1L;
        Produto produto = new Produto();
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto result = produtoService.enabledById(id);

        assertNotNull(result);
        assertTrue(result.isAtivo());
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

}
