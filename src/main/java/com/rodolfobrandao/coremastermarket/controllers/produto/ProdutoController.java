package com.rodolfobrandao.coremastermarket.controllers.produto;

import com.rodolfobrandao.coremastermarket.dtos.cliente.SearchCriteriaDTO;
import com.rodolfobrandao.coremastermarket.dtos.produto.CreateProdutoDTO;
import com.rodolfobrandao.coremastermarket.dtos.produto.DeleteProdutoDTO;
import com.rodolfobrandao.coremastermarket.dtos.pdv.ReadCodigoBarrasDTO;
import com.rodolfobrandao.coremastermarket.dtos.produto.UpdateProdutoDTO;
import com.rodolfobrandao.coremastermarket.entities.produto.Produto;
import com.rodolfobrandao.coremastermarket.services.produto.ProdutoService;
import com.rodolfobrandao.coremastermarket.tools.JsonUtil;
import com.rodolfobrandao.coremastermarket.tools.entities.PaginatedResponse;
import com.rodolfobrandao.coremastermarket.tools.PaginationRequestDTO;
import com.rodolfobrandao.coremastermarket.tools.PaginationUtils;
import com.rodolfobrandao.coremastermarket.tools.swagger.DefaultOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;

    }

    @PostMapping("/list")
    @DefaultOperation(summary = "Listar produtos", description = "Lista todos os produtos", tags = {"Produto"})
    public ResponseEntity<?> ListarProdutos(@RequestBody PaginationRequestDTO paginationRequest) {
        try {
            Page<Produto> produtos = produtoService.findAll(
                    paginationRequest.getPage(),
                    paginationRequest.getSize(),
                    paginationRequest.getSortname(),
                    paginationRequest.getSortorder()
            );
            PaginatedResponse<Produto> response = PaginationUtils.toPaginatedResponse(produtos);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @PostMapping("/findByQuery")
    @DefaultOperation(summary = "Buscar produtos", description = "Busca produtos por critérios", tags = {"Produto"})
    public ResponseEntity<?> findByQuery(@RequestBody SearchCriteriaDTO searchCriteria) {
        try {
            Page<Produto> produtos = produtoService.findByQuery(
                    searchCriteria.qtype(),
                    searchCriteria.query(),
                    searchCriteria.oper(),
                    searchCriteria.page(),
                    searchCriteria.rp(),
                    searchCriteria.sortname(),
                    searchCriteria.sortorder()
            );
            PaginatedResponse<Produto> response = PaginationUtils.toPaginatedResponse(produtos);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/create")
    @DefaultOperation(summary = "Criar produto", description = "Cria um novo produto", tags = {"Produto"})
    public ResponseEntity<String> createProduto(@RequestBody CreateProdutoDTO dto) {
        try {
            Produto produto = produtoService.create(dto);
            return ResponseEntity.ok(String.valueOf(produto));
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @PostMapping("/createMultiple")
    @DefaultOperation(summary = "Criar produtos", description = "Cria vários produtos", tags = {"Produto"})
    public ResponseEntity<?> createMultipleProdutos(@RequestBody List<CreateProdutoDTO> dtos) {
        try {
            Map<String, List<Produto>> resultado = produtoService.createMultiple(dtos);
            return ResponseEntity.ok(resultado);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @GetMapping("/findById")
    @DefaultOperation(summary = "Buscar produto por ID", description = "Busca um produto pelo ID", tags = {"Produto"})
    public ResponseEntity<?> findById(@RequestBody DeleteProdutoDTO deleteProdutoDTO) {
        try {
            Produto produto = produtoService.findById(deleteProdutoDTO.id());
            if (produto == null) {
                return ResponseEntity.status(404).body(JsonUtil.createMessageJson("Produto não encontrado", 404));
            }
            return ResponseEntity.ok(produto);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @PostMapping("/findByCodigoBarras")
    @DefaultOperation(summary = "Buscar produto por código de barras", description = "Busca um produto pelo código de barras", tags = {"Produto"})
    public ResponseEntity<?> findByCodigoBarras(@RequestBody ReadCodigoBarrasDTO readCodigoBarrasDTO) {
        try {
            String codigoBarras = readCodigoBarrasDTO.codigoBarras();
            Optional<Produto> produto = produtoService.findByCodigoBarras(codigoBarras);
            if (produto.isEmpty()) {
                return ResponseEntity.status(404).body(JsonUtil.createMessageJson("Produto não encontrado", 404));
            }
            return ResponseEntity.ok(produto.get());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @DefaultOperation(summary = "Deletar produto", description = "Deleta um produto", tags = {"Produto"})
    public ResponseEntity<?> deleteProduto(@RequestParam DeleteProdutoDTO deleteProdutoDTO) {
        Long id = deleteProdutoDTO.id();
        if (id == null) {
            return ResponseEntity.badRequest().body(JsonUtil.createMessageJson("ID do produto não pode ser nulo", 400));
        }
        if (id <= 0) {
            return ResponseEntity.badRequest().body(JsonUtil.createMessageJson("ID do produto deve ser maior que zero", 400));
        }
        //se nao existir o produto consultado pelo id retorne a mensagem
        try {
            Optional<Produto> optional = Optional.ofNullable(produtoService.findById(id));
            if (optional.isEmpty()) {
                return ResponseEntity.status(404).body(JsonUtil.createMessageJson("Produto não encontrado", 404));
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
        try {
            Produto produto = produtoService.deleteById(id);
            return ResponseEntity.ok(produto);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/enabled")
    @DefaultOperation(summary = "Ativar produto", description = "Ativa um produto", tags = {"Produto"})
    public ResponseEntity<?> enabledProduto(@RequestBody DeleteProdutoDTO deleteProdutoDTO) {
        Long id = deleteProdutoDTO.id();
        if (id == null) {
            return ResponseEntity.badRequest().body(JsonUtil.createMessageJson("ID do produto não pode ser nulo", 400));
        }
        if (id <= 0) {
            return ResponseEntity.badRequest().body(JsonUtil.createMessageJson("ID do produto deve ser maior que zero", 400));
        }
        //se nao existir o produto consultado pelo id retorne a mensagem
        try {
            Optional<Produto> optional = Optional.ofNullable(produtoService.findById(id));
            if (optional.isEmpty()) {
                return ResponseEntity.status(404).body(JsonUtil.createMessageJson("Produto não encontrado", 404));
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
        try {
            Produto produto = produtoService.enabledById(id);
            return ResponseEntity.ok(produto);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


    @PutMapping("/update")
    @DefaultOperation(summary = "Atualizar produto", description = "Atualiza um produto", tags = {"Produto"})
    public ResponseEntity<Produto> updateProduto(@RequestBody UpdateProdutoDTO dto) {
        Produto produto = produtoService.update(dto);
        return ResponseEntity.ok(produto);
    }

}
