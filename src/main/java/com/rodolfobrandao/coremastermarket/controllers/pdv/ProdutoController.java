package com.rodolfobrandao.coremastermarket.controllers.pdv;

import com.rodolfobrandao.coremastermarket.dtos.SearchCriteriaDTO;
import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateProdutoDTO;
import com.rodolfobrandao.coremastermarket.dtos.pdv.UpdateProdutoDTO;
import com.rodolfobrandao.coremastermarket.entities.Cliente;
import com.rodolfobrandao.coremastermarket.entities.pdv.Produto;
import com.rodolfobrandao.coremastermarket.services.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService ) {
        this.produtoService = produtoService;

    }

    @GetMapping("/list")
    public ResponseEntity<?> ListarProdutos() {
        try {
            Page<Produto> produtos = produtoService.findAll(1, ">=", 1, "id", "asc");
            return ResponseEntity.ok(produtos);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Erro ao listar produtos: " + ex.getMessage());
        }
    }

    @PostMapping("/findByQuery")
    public Page<Cliente> findByQuery(@RequestBody SearchCriteriaDTO searchCriteria) {
        return produtoService.findByQuery(
                searchCriteria.qtype(),
                searchCriteria.query(),
                searchCriteria.oper(),
                searchCriteria.page(),
                searchCriteria.rp(),
                searchCriteria.sortname(),
                searchCriteria.sortorder()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<Produto> createProduto(@RequestBody CreateProdutoDTO dto) {
        Produto produto = produtoService.create(dto);
        return ResponseEntity.ok(produto);
    }

    @PutMapping("/update")
    public ResponseEntity<Produto> updateProduto(@RequestBody UpdateProdutoDTO dto) {
        Produto produto = produtoService.update(dto);
        return ResponseEntity.ok(produto);
    }

}
