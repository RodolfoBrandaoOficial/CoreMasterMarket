package com.rodolfobrandao.coremastermarket.controllers.pdv;

import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateMarcaDTO;
import com.rodolfobrandao.coremastermarket.entities.pdv.Marca;
import com.rodolfobrandao.coremastermarket.services.MarcaService;
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
    @DefaultOperation(summary = "Listar marcas", description = "Lista todas as marcas", tags = {"Marca"})
    public ResponseEntity<?> listarMarcas(@RequestBody PaginationRequestDTO paginationRequest) {
        try {
            Page<Marca> marcasPage = marcaService.findAll(
                    paginationRequest.getPage(),
                    paginationRequest.getSize(),
                    paginationRequest.getSortname(),
                    paginationRequest.getSortorder()
            );
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
            throw new RuntimeException("Erro ao criar marca: " + ex.getMessage());
        }
    }
}
