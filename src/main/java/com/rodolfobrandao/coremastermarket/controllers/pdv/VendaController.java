package com.rodolfobrandao.coremastermarket.controllers.pdv;

import com.rodolfobrandao.coremastermarket.dtos.SearchCriteriaDTO;
import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateVendaDTO;
import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateVendaItemDTO;
import com.rodolfobrandao.coremastermarket.entities.Cliente;
import com.rodolfobrandao.coremastermarket.entities.pdv.Venda;
import com.rodolfobrandao.coremastermarket.entities.pdv.VendaItem;
import com.rodolfobrandao.coremastermarket.services.ClienteService;
import com.rodolfobrandao.coremastermarket.services.VendaService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/v1/vendas")
public class VendaController {

    private final VendaService vendaService;
    private final ClienteService clienteService;

    public VendaController(VendaService vendaService,ClienteService clienteService) {
        this.vendaService = vendaService;
        this.clienteService = clienteService;
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarVendas() {
        try {
            return ResponseEntity.ok(vendaService.findAll(1, ">=", 1, "id", "asc"));
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Erro ao listar vendas: " + ex.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createVenda(@RequestBody CreateVendaDTO vendaDTO) {
        try {
            Optional<Cliente> clienteOpt = clienteService.findById(vendaDTO.idCliente());
            if (clienteOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
            }
            Cliente cliente = clienteOpt.get();

            // Cria um mapa para armazenar os itens com idProduto como chave
            //* O mapa é usado para evitar a duplicação de itens
            Map<Long, VendaItem> itemMap = new HashMap<>();

            for (CreateVendaItemDTO itemDTO : vendaDTO.listVendaItens()) {
                VendaItem existingItem = itemMap.get(itemDTO.idProduto());
                if (existingItem != null) {
                    // Atualiza a quantidade do item existente usando BigDecimal.add
                    BigDecimal novaQuantidade = existingItem.getQuantidade().add(itemDTO.quantidade());
                    existingItem.setQuantidade(novaQuantidade);
                    existingItem.setDesconto(itemDTO.desconto());
                    existingItem.setAcrescimo(itemDTO.acrescimo());
                } else {
                    // Cria um novo item
                    VendaItem item = new VendaItem(itemDTO.quantidade(), itemDTO.desconto(), itemDTO.acrescimo(), itemDTO.idProduto(), cliente);
                    itemMap.put(itemDTO.idProduto(), item);
                }
            }

            // Converte o mapa de itens para uma lista
            List<VendaItem> listItens = new ArrayList<>(itemMap.values());

            Venda venda = new Venda(
                    vendaDTO.dataHoraInicio(),
                    vendaDTO.dataHoraTermino(),
                    vendaDTO.observacao(),
                    vendaDTO.pdv(),
                    listItens,
                    Optional.of(cliente)
            );

            Venda newVenda = vendaService.create(venda);
            return ResponseEntity.ok(newVenda);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Erro ao criar venda: " + ex.getMessage());
        }
    }


    @PostMapping("/findById")
    public ResponseEntity<?> findById(@RequestBody Long id) {
        try {
            Venda venda = vendaService.findById(id);
            if (venda == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venda não encontrada");
            }
            return ResponseEntity.ok(venda);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Erro ao buscar venda: " + ex.getMessage());
        }
    }

    @PostMapping("/findByQuery")
    public ResponseEntity<?> findByQuery(@RequestBody SearchCriteriaDTO searchCriteria) {
        try {
            Page<Cliente> clientes = vendaService.findByQuery(
                    searchCriteria.qtype(),
                    searchCriteria.query(),
                    searchCriteria.oper(),
                    searchCriteria.page(),
                    searchCriteria.rp(),
                    searchCriteria.sortname(),
                    searchCriteria.sortorder()
            );
            return ResponseEntity.ok(clientes);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Erro ao buscar vendas: " + ex.getMessage());
        }
    }
}
