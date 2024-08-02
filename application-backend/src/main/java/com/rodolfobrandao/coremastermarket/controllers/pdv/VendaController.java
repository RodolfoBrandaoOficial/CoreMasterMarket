package com.rodolfobrandao.coremastermarket.controllers.pdv;

import com.rodolfobrandao.coremastermarket.dtos.pdv.*;
import com.rodolfobrandao.coremastermarket.entities.cliente.Cliente;
import com.rodolfobrandao.coremastermarket.entities.pdv.Venda;
import com.rodolfobrandao.coremastermarket.entities.pdv.VendaItem;
import com.rodolfobrandao.coremastermarket.services.cliente.ClienteService;
import com.rodolfobrandao.coremastermarket.services.pve.ModoPagamentoService;
import com.rodolfobrandao.coremastermarket.services.produto.ProdutoService;
import com.rodolfobrandao.coremastermarket.services.pve.VendaService;
import com.rodolfobrandao.coremastermarket.tools.CustomException;
import com.rodolfobrandao.coremastermarket.tools.JsonUtil;
import com.rodolfobrandao.coremastermarket.tools.entities.PaginatedResponse;
import com.rodolfobrandao.coremastermarket.tools.swagger.DefaultOperation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/v1/vendas")
@CrossOrigin(origins = "*")
public class VendaController {

    private final ClienteService clienteService;
    private final ModoPagamentoService modoPagamentoService;
    private final VendaService vendaService;
    private final ProdutoService produtoService;

    public VendaController(ClienteService clienteService, ModoPagamentoService modoPagamentoService, VendaService vendaService, ProdutoService produtoService) {
        this.clienteService = clienteService;
        this.modoPagamentoService = modoPagamentoService;
        this.vendaService = vendaService;
        this.produtoService = produtoService;
    }

    @GetMapping("/listar")
    @DefaultOperation(summary = "Listar vendas", description = "Lista todas as vendas", tags = {"Venda"})
    public ResponseEntity<List<VendaDTO>> findAll() {
        List<VendaDTO> vendas = vendaService.findAll();
        return ResponseEntity.ok(vendas);
    }

    @PostMapping("/create")
    @DefaultOperation(summary = "Criar venda", description = "Cria uma nova venda", tags = {"Venda"})
    public ResponseEntity<VendaDTO> createVenda(@RequestBody CreateVendaDTO vendaDTO) {
        Optional<Cliente> clienteOpt = clienteService.findById(vendaDTO.idCliente());
        if (clienteOpt.isEmpty()) {
            throw new CustomException("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        Cliente cliente = clienteOpt.get();
        Map<Long, VendaItem> itemMap = new HashMap<>();

        for (CreateVendaItemDTO itemDTO : vendaDTO.listVendaItens()) {
            VendaItem existingItem = itemMap.get(itemDTO.idProduto());
            if (existingItem != null) {
                BigDecimal novaQuantidade = existingItem.getQuantidade().add(itemDTO.quantidade());
                existingItem.setQuantidade(novaQuantidade);
                existingItem.setDesconto(itemDTO.desconto());
                existingItem.setAcrescimo(itemDTO.acrescimo());
            } else {
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

        List<VendaItem> listItens = new ArrayList<>(itemMap.values());

        Venda venda = new Venda(
                vendaDTO.dataHoraInicio(),
                vendaDTO.dataHoraTermino(),
                vendaDTO.observacao(),
                vendaDTO.pdv(),
                listItens,
                vendaDTO.modoPagamento(),
                cliente
        );

        for (VendaItem item : listItens) {
            item.setVenda(venda);
            produtoService.atualizarEstoque(item.getIdProduto(), item.getQuantidade()); // Atualiza o estoque
        }

        Venda newVenda = vendaService.create(venda);
        VendaDTO vendaDTOResponse = vendaService.convertToVendaDTO(newVenda);
        return ResponseEntity.ok(vendaDTOResponse);
    }

    @PostMapping("/findById")
    @DefaultOperation(summary = "Buscar venda por ID", description = "Busca uma venda pelo ID", tags = {"Venda"})
    public ResponseEntity<VendaDTO> findById(@RequestBody ReadVendaDTO readVendaDTO) {
        VendaDTO vendaDTO = vendaService.findById(readVendaDTO.id());
        return ResponseEntity.ok(vendaDTO);
    }

    @PutMapping("/update")
    @DefaultOperation(summary = "Atualizar venda", description = "Atualiza uma venda", tags = {"Venda"})
    public ResponseEntity<VendaDTO> updateVenda(@RequestBody @Valid UpdateVendaDTO updateVendaDTO) {
        Venda venda = vendaService.update(updateVendaDTO);
        VendaDTO vendaDTO = vendaService.convertToVendaDTO(venda);
        return ResponseEntity.ok(vendaDTO);
    }

    @DeleteMapping("/delete")
    @DefaultOperation(summary = "Deletar venda", description = "Deleta uma venda", tags = {"Venda"})
    public ResponseEntity<String> deleteVenda(@RequestParam Long id) {
        vendaService.deleteVenda(id);
        return ResponseEntity.ok(JsonUtil.createMessageJson("Venda deletada com sucesso e estoque desvolvido!", 200));
    }
}
