package com.rodolfobrandao.coremastermarket.controllers.pdv;

import com.rodolfobrandao.coremastermarket.dtos.SearchCriteriaDTO;
import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateVendaDTO;
import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateVendaItemDTO;
import com.rodolfobrandao.coremastermarket.entities.Cliente;
import com.rodolfobrandao.coremastermarket.entities.pdv.ModoPagamento;
import com.rodolfobrandao.coremastermarket.entities.pdv.Venda;
import com.rodolfobrandao.coremastermarket.entities.pdv.VendaItem;
import com.rodolfobrandao.coremastermarket.repositories.VendaRepository;
import com.rodolfobrandao.coremastermarket.services.ClienteService;
import com.rodolfobrandao.coremastermarket.services.ModoPagamentoService;
import com.rodolfobrandao.coremastermarket.services.VendaService;
import com.rodolfobrandao.coremastermarket.tools.CustomException;
import com.rodolfobrandao.coremastermarket.tools.swagger.DefaultOperation;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

import static com.rodolfobrandao.coremastermarket.specifications.GenericSpecification.buildSpecification;

@RestController
@RequestMapping("/api/v1/vendas")
public class VendaController {

    private final ClienteService clienteService;
    private final ModoPagamentoService modoPagamentoService; // Adicione esta variável
    private final VendaService vendaService;

    public VendaController(ClienteService clienteService, ModoPagamentoService modoPagamentoService, VendaService vendaService) {
        this.clienteService = clienteService;
        this.modoPagamentoService = modoPagamentoService; // Inicialize a variável
        this.vendaService = vendaService;
    }

    @GetMapping("/listar")
    @DefaultOperation(summary = "Listar vendas", description = "Lista todas as vendas", tags = {"Venda"})
    public ResponseEntity<?> listarVendas() {
        return ResponseEntity.ok(vendaService.findAll(1, ">=",100000, "id", "asc"));
    }

    @PostMapping("/create")
    @DefaultOperation(summary = "Criar venda", description = "Cria uma nova venda", tags = {"Venda"})
    public ResponseEntity<?> createVenda(@RequestBody CreateVendaDTO vendaDTO) {
        Optional<Cliente> clienteOpt = clienteService.findById(vendaDTO.idCliente());
        if (clienteOpt.isEmpty()) {
            throw new CustomException("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        Cliente cliente = clienteOpt.get();

        // Cria um mapa para armazenar os itens com idProduto como chave
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
                VendaItem item = new VendaItem(
                        itemDTO.quantidade(),
                        itemDTO.desconto(),
                        itemDTO.acrescimo(),
                        itemDTO.idProduto(),
                        null, // Será definido depois
                        cliente
                );
                itemMap.put(itemDTO.idProduto(), item);
            }
        }

        // Converte o mapa de itens para uma lista
        List<VendaItem> listItens = new ArrayList<>(itemMap.values());

        // Cria a venda com o cliente e os itens
        Venda venda = new Venda(
                vendaDTO.dataHoraInicio(),
                vendaDTO.dataHoraTermino(),
                vendaDTO.observacao(),
                vendaDTO.pdv(),
                listItens,
                vendaDTO.modoPagamento(),
                cliente
        );

        // Atualiza os itens para associar a venda corretamente
        for (VendaItem item : listItens) {
            item.setVenda(venda);
        }

        // Salva a venda e retorna a resposta
        Venda newVenda = vendaService.create(venda);
        return ResponseEntity.ok(newVenda); // Retorna a venda completa
    }




    @PostMapping("/findById")
    @DefaultOperation(summary = "Buscar venda por ID", description = "Busca uma venda pelo ID", tags = {"Venda"})
    public ResponseEntity<?> findById(@RequestBody Long id) {
        Venda venda = vendaService.findById(id);
        if (venda == null) {
            throw new CustomException("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(venda);
    }

    @PostMapping("/findByQuery")
    @DefaultOperation(summary = "Buscar vendas por consulta", description = "Busca vendas por consulta", tags = {"Venda"})
    public ResponseEntity<?> findByQuery(@RequestBody SearchCriteriaDTO searchCriteria) {
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
    }

}
