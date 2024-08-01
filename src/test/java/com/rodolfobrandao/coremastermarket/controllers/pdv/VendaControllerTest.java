package com.rodolfobrandao.coremastermarket.controllers.pdv;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rodolfobrandao.coremastermarket.dtos.pdv.*;
import com.rodolfobrandao.coremastermarket.entities.cliente.Cliente;
import com.rodolfobrandao.coremastermarket.entities.pdv.Venda;
import com.rodolfobrandao.coremastermarket.services.cliente.ClienteService;
import com.rodolfobrandao.coremastermarket.services.produto.ProdutoService;
import com.rodolfobrandao.coremastermarket.services.pve.ModoPagamentoService;
import com.rodolfobrandao.coremastermarket.services.pve.VendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VendaControllerTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private ModoPagamentoService modoPagamentoService;

    @Mock
    private VendaService vendaService;

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private VendaController vendaController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vendaController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Registra o módulo para suportar LocalDateTime
    }

    /**
     * Testa a obtenção de todas as vendas.
     *
     * Este teste prepara uma lista de objetos `VendaDTO` e configura o mock para retornar essa lista quando
     * o método `findAll` do serviço `vendaService` é chamado. Em seguida, realiza uma solicitação GET para o
     * endpoint `/api/v1/vendas/listar` e verifica se a resposta contém os dados esperados.
     *
     * @throws Exception se ocorrer algum erro durante a execução do teste.
     */
    @Test
    void testFindAll() throws Exception {
        // Prepare mock data
        ClienteDTO clienteDTO = new ClienteDTO(1L, "Cliente Teste", "12345678901");
        VendaDTO vendaDTO1 = new VendaDTO(1L, LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Observação 1", "Observação 1", clienteDTO, new ArrayList<>());
        VendaDTO vendaDTO2 = new VendaDTO(2L, LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Observação 2", "Observação 2", clienteDTO, new ArrayList<>());
        List<VendaDTO> vendaDTOList = Arrays.asList(vendaDTO1, vendaDTO2);

        when(vendaService.findAll()).thenReturn(vendaDTOList);

        // Execute the GET request and verify the response
        mockMvc.perform(get("/api/v1/vendas/listar")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());

        verify(vendaService, times(1)).findAll();
    }

    /**
     * Testa a criação de uma nova venda.
     *
     * Este teste prepara um objeto `CreateVendaDTO` com dados de venda e um objeto `VendaDTO` com a resposta
     * esperada. O mock é configurado para retornar a venda criada e convertida para `VendaDTO` quando os métodos
     * do serviço são chamados. Em seguida, realiza uma solicitação POST para o endpoint `/api/v1/vendas/create`
     * e verifica se a resposta contém os dados da venda criada.
     *
     * @throws Exception se ocorrer algum erro durante a execução do teste.
     */
    @Test
    void testCreateVenda() throws Exception {
        // Prepare mock data
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        CreateVendaDTO createVendaDTO = new CreateVendaDTO(
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                null,
                "Observação",
                1L, // PDV
                Collections.singletonList(new CreateVendaItemDTO(
                        BigDecimal.valueOf(2),
                        BigDecimal.valueOf(10),
                        BigDecimal.ZERO,
                        BigDecimal.ONE,
                        1L, // ID do Produto
                        1L, // ID da Venda
                        1L  // ID do Cliente
                )),
                1L // ID do Cliente
        );

        Venda venda = new Venda();
        VendaDTO vendaDTOResponse = new VendaDTO(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                "Observação",
                "Observação",
                new ClienteDTO(cliente.getId(), "Cliente Teste", "12345678901"),
                new ArrayList<>()
        );

        when(clienteService.findById(createVendaDTO.idCliente())).thenReturn(Optional.of(cliente));
        when(vendaService.create(any(Venda.class))).thenReturn(venda);
        when(vendaService.convertToVendaDTO(venda)).thenReturn(vendaDTOResponse);

        // Execute the POST request and verify the response
        mockMvc.perform(post("/api/v1/vendas/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createVendaDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cliente").exists())
                .andExpect(jsonPath("$.cliente.nome").value("Cliente Teste"));

        verify(clienteService, times(1)).findById(createVendaDTO.idCliente());
        verify(vendaService, times(1)).create(any(Venda.class));
        verify(vendaService, times(1)).convertToVendaDTO(any(Venda.class));
    }

    /**
     * Testa a obtenção de uma venda por ID.
     *
     * Este teste configura o mock para retornar um `VendaDTO` quando o método `findById` do serviço `vendaService`
     * é chamado com um ID específico. Em seguida, realiza uma solicitação POST para o endpoint `/api/v1/vendas/findById`
     * e verifica se a resposta contém os dados da venda.
     *
     * @throws Exception se ocorrer algum erro durante a execução do teste.
     */
    @Test
    void testFindById() throws Exception {
        // Prepare mock data
        ClienteDTO clienteDTO = new ClienteDTO(1L, "Cliente Teste", "12345678901");
        VendaDTO vendaDTO = new VendaDTO(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                "Observação",
                "Observação",
                clienteDTO,
                new ArrayList<>()
        );
        when(vendaService.findById(anyLong())).thenReturn(vendaDTO);

        // Execute the POST request and verify the response
        mockMvc.perform(post("/api/v1/vendas/findById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ReadVendaDTO(1L))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());

        verify(vendaService, times(1)).findById(anyLong());
    }

    /**
     * Testa a atualização de uma venda.
     *
     * Este teste prepara um objeto `UpdateVendaDTO` com dados de atualização e configura o mock para retornar
     * a venda atualizada quando o método `update` do serviço `vendaService` é chamado. Em seguida, realiza uma
     * solicitação PUT para o endpoint `/api/v1/vendas/update` e verifica se a resposta contém os dados da venda
     * atualizada.
     *
     * @throws Exception se ocorrer algum erro durante a execução do teste.
     */
    @Test
    void testUpdateVenda() throws Exception {
        // Prepare mock data
        Venda venda = new Venda();
        UpdateVendaDTO updateVendaDTO = new UpdateVendaDTO(
                1L,
                null,
                "Observação",
                "Observação"
        );

        when(vendaService.update(any(UpdateVendaDTO.class))).thenReturn(venda);

        // Execute the PUT request and verify the response
        mockMvc.perform(put("/api/v1/vendas/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateVendaDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());

        verify(vendaService, times(1)).update(any(UpdateVendaDTO.class));
    }

    /**
     * Testa a atualização de uma venda por ID.
     *
     * Este teste configura o mock para retornar uma venda atualizada quando o método `updateById` do serviço
     * `vendaService` é chamado com um ID e uma venda. Em seguida, realiza uma solicitação PUT para o endpoint
     * `/api/v1/vendas/updateById` e verifica se a resposta contém os dados da venda atualizada.
     *
     * @throws Exception se ocorrer algum erro durante a execução do teste.
     */
    @Test
    void testUpdateVendaById() throws Exception {
        // Prepare mock data
        Venda venda = new Venda();
        when(vendaService.updateById(anyLong(), any(Venda.class))).thenReturn(venda);

        // Execute the PUT request and verify the response
        mockMvc.perform(put("/api/v1/vendas/updateById")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venda)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());

        verify(vendaService, times(1)).updateById(anyLong(), any(Venda.class));
    }
}
