package com.rodolfobrandao.coremastermarket.controllers;

import com.rodolfobrandao.coremastermarket.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;


import static java.lang.reflect.Array.get;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class ClienteControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ClienteController clienteController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }


    @Test
    void listarClientes() throws Exception {
        Mockito.when(clienteService.findAll(anyInt(), anyString(), anyInt(), anyString(), anyString())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clientes/listar"))
                .andDo(print())
                .andExpect(status().isOk());
    }




    @Test
    void findByQuery() {

    }

    @Test
    void buscarCliente() {

    }

    @Test
    void cadastrarCliente() {
    }
}
