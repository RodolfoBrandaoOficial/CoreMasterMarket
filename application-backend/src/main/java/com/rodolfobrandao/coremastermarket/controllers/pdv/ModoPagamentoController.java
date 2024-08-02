package com.rodolfobrandao.coremastermarket.controllers.pdv;

import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateModoPagamentoDTO;
import com.rodolfobrandao.coremastermarket.dtos.pdv.UpdateModoPagamentoDTO;
import com.rodolfobrandao.coremastermarket.entities.pdv.ModoPagamento;
import com.rodolfobrandao.coremastermarket.services.pve.ModoPagamentoService;
import com.rodolfobrandao.coremastermarket.tools.swagger.DefaultOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/modo-pagamento")
public class ModoPagamentoController {

    private final ModoPagamentoService modoPagamentoService;

    public ModoPagamentoController(ModoPagamentoService modoPagamentoService) {
        this.modoPagamentoService = modoPagamentoService;
    }

    /**
     * Busca um modo de pagamento pelo ID
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @DefaultOperation(summary = "Buscar modo de pagamento por ID", description = "Busca um modo de pagamento pelo ID", tags = {"ModoPagamento"})
    public ResponseEntity<ModoPagamento> getModoPagamentoById(@PathVariable Long id) {
        Optional<ModoPagamento> modoPagamento = modoPagamentoService.findById(id);
        return modoPagamento.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Lista todos os modos de pagamento
     *
     * @return
     */
    @GetMapping("/list")
    @DefaultOperation(summary = "Listar modos de pagamento", description = "Lista todos os modos de pagamento", tags = {"ModoPagamento"})
    public ResponseEntity<List<ModoPagamento>> getAllModoPagamentos() {
        List<ModoPagamento> modosPagamentos = modoPagamentoService.findAll();
        return ResponseEntity.ok(modosPagamentos);
    }

    /**
     * Cria um novo modo de pagamento
     *
     * @param dto
     * @return
     */
    @PostMapping("/create")
    @DefaultOperation(summary = "Criar modo de pagamento", description = "Cria um novo modo de pagamento", tags = {"ModoPagamento"})
    public ResponseEntity<ModoPagamento> createModoPagamento(@RequestBody CreateModoPagamentoDTO dto) {
        ModoPagamento novoModoPagamento = modoPagamentoService.create(dto);
        return ResponseEntity.status(HttpStatus.OK).body(novoModoPagamento);
    }

    /**
     * Atualiza um modo de pagamento existente
     *
     * @param id
     * @param dto
     * @return
     */
    @PutMapping("/update/{id}")
    @DefaultOperation(summary = "Atualizar modo de pagamento", description = "Atualiza um modo de pagamento existente", tags = {"ModoPagamento"})
    public ResponseEntity<ModoPagamento> updateModoPagamento(@PathVariable Long id, @RequestBody UpdateModoPagamentoDTO dto) {
        ModoPagamento modoPagamentoAtualizado = modoPagamentoService.update(id, dto);
        return ResponseEntity.ok(modoPagamentoAtualizado);
    }

    /**
     * Deleta um modo de pagamento existente
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @DefaultOperation(summary = "Deletar modo de pagamento", description = "Deleta um modo de pagamento pelo ID", tags = {"ModoPagamento"})
    public ResponseEntity<Void> deleteModoPagamento(@PathVariable Long id) {
        modoPagamentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
