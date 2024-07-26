package com.rodolfobrandao.coremastermarket.controllers.pdv;

import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateMarcaDTO;
import com.rodolfobrandao.coremastermarket.entities.pdv.Marca;
import com.rodolfobrandao.coremastermarket.services.MarcaService;
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
    public ResponseEntity<?> ListarMarcas() {
        try {
            Page<Marca> marcas = marcaService.findAll(1, ">=", 10, "id", "asc");
            return ResponseEntity.ok(marcas);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Erro ao listar marcas: " + ex.getMessage());
        }
    }
    @PostMapping("/create")
    public Marca create(@RequestBody CreateMarcaDTO dto) {
        return marcaService.create(dto);
    }
}
