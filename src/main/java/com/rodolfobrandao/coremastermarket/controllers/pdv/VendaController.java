package com.rodolfobrandao.coremastermarket.controllers.pdv;

import com.rodolfobrandao.coremastermarket.dtos.SearchCriteriaDTO;
import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateVendaDTO;
import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateVendaItemDTO;
import com.rodolfobrandao.coremastermarket.entities.Cliente;
import com.rodolfobrandao.coremastermarket.entities.pdv.Venda;
import com.rodolfobrandao.coremastermarket.entities.pdv.VendaItem;
import com.rodolfobrandao.coremastermarket.services.VendaService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }


    @GetMapping("/listar")
    public ResponseEntity<?> ListarVendas() {
        try {
            return ResponseEntity.ok(vendaService.findAll(1, ">=", 1, "id", "asc"));
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Erro ao listar vendas: " + ex.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Venda> putVenda(@RequestBody CreateVendaDTO vendaDTO) {
        List<VendaItem> listItens = new ArrayList<>();

        for (VendaItem item : vendaDTO.listVendaItens()) {
            listItens.add(
                    new VendaItem(item.getQuantidade(), item.getDesconto(), item.getAcrescimo(), item.getIdProduto())
            );
        }

        Venda venda = new Venda(
                vendaDTO.dataHoraInicio(),
                vendaDTO.dataHoraTermino(),
                vendaDTO.observacao(),
                vendaDTO.pdv(),
                listItens);

        Venda newVenda = vendaService.create(venda);

        ResponseEntity responseEntity = new ResponseEntity<>("" + newVenda, HttpStatus.OK);
        return responseEntity;
    }

                        @PostMapping("/findById")
    public Venda findById(@RequestBody Long id) {
        return vendaService.findById(id);
    }
    @PostMapping("/findByQuery")
    public Page<Cliente> findByQuery(@RequestBody SearchCriteriaDTO searchCriteria) {
        return vendaService.findByQuery(
                searchCriteria.qtype(),
                searchCriteria.query(),
                searchCriteria.oper(),
                searchCriteria.page(),
                searchCriteria.rp(),
                searchCriteria.sortname(),
                searchCriteria.sortorder()
        );
    }


}
