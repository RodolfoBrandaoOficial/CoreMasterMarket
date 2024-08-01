package com.rodolfobrandao.coremastermarket.controllers.produto;

import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateMarcaDTO;
import com.rodolfobrandao.coremastermarket.entities.produto.Marca;
import com.rodolfobrandao.coremastermarket.services.produto.MarcaService;
import com.rodolfobrandao.coremastermarket.tools.JsonUtil;
import com.rodolfobrandao.coremastermarket.tools.entities.PaginatedResponse;
import com.rodolfobrandao.coremastermarket.tools.PaginationRequestDTO;
import com.rodolfobrandao.coremastermarket.tools.PaginationUtils;
import com.rodolfobrandao.coremastermarket.tools.swagger.DefaultOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/marcas")
@RequiredArgsConstructor
public class MarcaController {
    private final MarcaService marcaService;

    @GetMapping("/list")
    public ResponseEntity<?> listarMarcas(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortname,
            @RequestParam String sortorder) {
        try {
            Page<Marca> marcasPage = marcaService.findAll(page, size, sortname, sortorder);
            PaginatedResponse<Marca> response = PaginationUtils.toPaginatedResponse(marcasPage);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Erro ao listar marcas: " + ex.getMessage());
        }
    }


    @PostMapping("/create")
    @DefaultOperation(summary = "Criar marca", description = "Cria uma nova marca", tags = {"Marca"})
    public Marca create(@RequestBody CreateMarcaDTO dto) {
        try {
            return marcaService.create(dto);
        } catch (Exception ex) {
            throw new RuntimeException(JsonUtil.createMessageJson(ex.getMessage(), 500));
        }
    }

    @DeleteMapping("/delete")
    @DefaultOperation(summary = "Deletar marca", description = "Deleta uma marca", tags = {"Marca"})
    public ResponseEntity delete(@RequestParam Long id) {
        try {
            return marcaService.delete(id);
        } catch (Exception ex) {
            throw new RuntimeException(JsonUtil.createMessageJson(ex.getMessage(), 500));
        }
    }

    @PostMapping("/update")
    @DefaultOperation(summary = "Atualizar marca", description = "Atualiza uma marca", tags = {"Marca"})
    public Marca update(@RequestParam Long id, @RequestBody CreateMarcaDTO dto) {
        try {
            return marcaService.update(id, dto);
        } catch (Exception ex) {
            throw new RuntimeException(JsonUtil.createMessageJson("Erro ao atualizar marca: " + ex.getMessage(), 500));
        }
    }
}
