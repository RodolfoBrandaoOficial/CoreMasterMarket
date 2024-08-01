package com.rodolfobrandao.coremastermarket.controllers.produto;

import com.rodolfobrandao.coremastermarket.controllers.produto.ProdutoController;
import com.rodolfobrandao.coremastermarket.dtos.produto.CreateProdutoDTO;
import com.rodolfobrandao.coremastermarket.entities.produto.Produto;
import com.rodolfobrandao.coremastermarket.services.produto.ProdutoService;

import com.rodolfobrandao.coremastermarket.tools.PaginationRequestDTO;
import com.rodolfobrandao.coremastermarket.tools.PaginationUtils;
import com.rodolfobrandao.coremastermarket.tools.entities.PaginatedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarProdutos() throws Exception {
        PaginationRequestDTO paginationRequest = new PaginationRequestDTO();
        PaginatedResponse<Produto> paginatedResponse = new PaginatedResponse<>(0, 0, 10, 1, List.of());

        // Mock static method if necessary
        try (MockedStatic<PaginationUtils> utilities = Mockito.mockStatic(PaginationUtils.class)) {
            utilities.when(() -> PaginationUtils.toPaginatedResponse(any(Page.class))).thenReturn(paginatedResponse);

            when(produtoService.findAll(anyInt(), anyInt(), anyString(), anyString())).thenReturn(Page.empty());

            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/produtos/list")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"page\":1,\"size\":10,\"sortname\":\"descricao\",\"sortorder\":\"asc\"}"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json("{\"content\":[],\"page\":1,\"size\":10,\"totalElements\":0}"))
                    .andDo(print());

            verify(produtoService, times(1)).findAll(anyInt(), anyInt(), anyString(), anyString());
        }
    }

    @Test
    void testCreateProduto() throws Exception {
        CreateProdutoDTO createProdutoDTO = new CreateProdutoDTO("1234567890123", "Produto Teste", BigDecimal.TEN, "Embalagem", 10L, null, null, 1L, true);
        Produto produto = new Produto();
        when(produtoService.create(any(CreateProdutoDTO.class))).thenReturn(produto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/produtos/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codigoBarras\":\"1234567890123\",\"descricao\":\"Produto Teste\",\"precoVenda\":10,\"tipoEmbalagem\":\"Embalagem\",\"quantidade\":10,\"criadoEm\":null,\"atualizadoEm\":null,\"marca\":1,\"ativo\":true}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(produto.toString()))
                .andDo(print());

        verify(produtoService, times(1)).create(any(CreateProdutoDTO.class));
    }
}
