package com.rodolfobrandao.coremastermarket.controllers.produto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateMarcaDTO;
import com.rodolfobrandao.coremastermarket.entities.produto.Marca;
import com.rodolfobrandao.coremastermarket.services.produto.MarcaService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.rodolfobrandao.coremastermarket.tools.PaginationRequestDTO;
import com.rodolfobrandao.coremastermarket.tools.PaginationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MarcaControllerTest {

    @Mock
    private MarcaService marcaService;

    @InjectMocks
    private MarcaController marcaController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(marcaController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Testa a listagem de marcas com paginação.
     *
     * Este teste configura o mock para retornar uma página de marcas e verifica a resposta da solicitação POST para
     * o endpoint `/api/v1/marcas/list`.
     *
     * @throws Exception se ocorrer algum erro durante a execução do teste.
     */
    @Test
    @WithMockUser
    public void testListarMarcas() throws Exception {
        List<Marca> marcas = Arrays.asList(new Marca(1L, "Marca A", "1"), new Marca(2L, "Marca B", "1"));
        Page<Marca> pageMarcas = new PageImpl<>(marcas);

        when(marcaService.findAll(anyInt(), anyInt(), anyString(), anyString())).thenReturn(pageMarcas);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/marcas/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "1")
                        .param("size", "10")
                        .param("sortname", "id")
                        .param("sortorder", "asc"))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(PaginationUtils.toPaginatedResponse(pageMarcas))));
    }

    /**
     * Testa a criação de uma nova marca.
     *
     * Este teste configura o mock para retornar uma marca criada e verifica a resposta da solicitação POST para o
     * endpoint `/api/v1/marcas/create`.
     *
     * @throws Exception se ocorrer algum erro durante a execução do teste.
     */
    @Test
    void testCreateMarca() throws Exception {
        // Prepare mock data
        CreateMarcaDTO createMarcaDTO = new CreateMarcaDTO("Marca Teste", "Descrição Teste");

        Marca marca = new Marca();
        marca.setId(1L);
        marca.setNome("Marca Teste");

        when(marcaService.create(any(CreateMarcaDTO.class))).thenReturn(marca);

        // Execute the POST request and verify the response
        mockMvc.perform(post("/api/v1/marcas/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createMarcaDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Marca Teste"));

        verify(marcaService, times(1)).create(any(CreateMarcaDTO.class));
    }

}
