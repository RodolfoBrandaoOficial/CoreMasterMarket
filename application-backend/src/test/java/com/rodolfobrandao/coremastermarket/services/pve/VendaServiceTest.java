package com.rodolfobrandao.coremastermarket.services.pve;

import com.rodolfobrandao.coremastermarket.dtos.pdv.ClienteDTO;
import com.rodolfobrandao.coremastermarket.dtos.pdv.VendaDTO;
import com.rodolfobrandao.coremastermarket.entities.cliente.Cliente;
import com.rodolfobrandao.coremastermarket.entities.pdv.ModoPagamento;
import com.rodolfobrandao.coremastermarket.entities.pdv.Venda;
import com.rodolfobrandao.coremastermarket.entities.pdv.VendaItem;
import com.rodolfobrandao.coremastermarket.repositories.pdv.VendaRepository;
import com.rodolfobrandao.coremastermarket.repositories.tools.GenericQueryService;
import com.rodolfobrandao.coremastermarket.tools.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Teste para a classe VendaService.
 *
 * Este teste verifica o comportamento do método findAll() da classe VendaService.
 * O método findAll() recupera todas as vendas do repositório e as converte em DTOs.
 */
class VendaServiceTest {

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private GenericQueryService<Venda> genericQueryService;

    @InjectMocks
    private VendaService vendaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa o método findAll() da VendaService.
     *
     * Este teste verifica se o método findAll() recupera corretamente todas as vendas do repositório,
     * converte as entidades Venda em DTOs e lida corretamente com vendas que possuem ou não clientes.
     */
    @Test
    void testFindAll() {
        // Prepare mock data
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");
        cliente.setCpfCnpj("12345678901");

        VendaItem item1 = new VendaItem();
        item1.setId(1L);
        item1.setQuantidade(BigDecimal.valueOf(2));
        item1.setDesconto(BigDecimal.valueOf(10));
        item1.setAcrescimo(BigDecimal.ZERO);
        item1.setIdProduto(1L);

        Venda venda1 = new Venda();
        venda1.setId(1L);
        venda1.setDataHoraInicio(LocalDateTime.now().minusDays(1));
        venda1.setDataHoraTermino(LocalDateTime.now());
        venda1.setObservacao("Observação 1");
        venda1.setModoPagamento(null); // Define o modo de pagamento como null para testar este caso
        venda1.setCliente(cliente);
        venda1.setListVendaItens(Collections.singletonList(item1));

        Venda venda2 = new Venda();
        venda2.setId(2L);
        venda2.setDataHoraInicio(LocalDateTime.now().minusDays(2));
        venda2.setDataHoraTermino(LocalDateTime.now().minusDays(1));
        venda2.setObservacao("Observação 2");
        venda2.setModoPagamento(null); // Define o modo de pagamento como null para testar este caso
        venda2.setCliente(null); // Cliente nulo
        venda2.setListVendaItens(Collections.emptyList());

        List<Venda> vendas = Arrays.asList(venda1, venda2);

        // Mock the repository method
        when(vendaRepository.findAll()).thenReturn(vendas);

        // Execute the method under test
        List<VendaDTO> result = vendaService.findAll();

        // Verify results
        assertNotNull(result, "A lista de vendas DTO não deve ser nula.");
        assertEquals(2, result.size(), "A lista de vendas DTO deve conter dois itens.");

        VendaDTO dto1 = result.get(0);
        assertNotNull(dto1.cliente(), "O cliente do primeiro DTO não deve ser nulo.");
        assertEquals("Cliente Teste", dto1.cliente().nome(), "O nome do cliente no primeiro DTO está incorreto.");

        VendaDTO dto2 = result.get(1);
        assertNull(dto2.cliente(), "O cliente do segundo DTO deve ser nulo.");
    }

}
