package com.rodolfobrandao.coremastermarket.controllers.cliente;

import com.rodolfobrandao.coremastermarket.controllers.cliente.ClienteController;
import com.rodolfobrandao.coremastermarket.services.cliente.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
