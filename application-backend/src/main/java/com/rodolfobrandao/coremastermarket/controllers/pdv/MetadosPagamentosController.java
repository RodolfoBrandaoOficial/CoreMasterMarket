package com.rodolfobrandao.coremastermarket.controllers.pdv;

import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateMetadosPagamentosDTO;
import com.rodolfobrandao.coremastermarket.dtos.pdv.DeleteMetadosPagamentosDTO;
import com.rodolfobrandao.coremastermarket.entities.pdv.MetadosPagamentos;
import com.rodolfobrandao.coremastermarket.services.pve.MetadosPagamentoService;
import com.rodolfobrandao.coremastermarket.tools.JsonUtil;
import com.rodolfobrandao.coremastermarket.tools.swagger.DefaultOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/metados-pagamentos")
public class MetadosPagamentosController {

    private final MetadosPagamentoService metadosPagamentoService;

    public MetadosPagamentosController(MetadosPagamentoService metadosPagamentoService) {
        this.metadosPagamentoService = metadosPagamentoService;
    }

    @PostMapping("/create")
    @DefaultOperation(summary = "Criar metado de pagamento", description = "Cria um novo metado de pagamento", tags = {"Metado de pagamento"})
    public ResponseEntity<?> create(@RequestBody CreateMetadosPagamentosDTO dto) {
        try {
            MetadosPagamentos metadosPagamentos = metadosPagamentoService.create(dto);
            return ResponseEntity.status(HttpStatus.OK).body(metadosPagamentos);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar metado de pagamento: " + ex.getMessage());
        }
    }
    @PutMapping("/update")
    @DefaultOperation(summary = "Atualizar metado de pagamento", description = "Atualiza um metado de pagamento", tags = {"Metado de pagamento"})
    public ResponseEntity<?> update(@RequestBody CreateMetadosPagamentosDTO dto) {
        try {
            MetadosPagamentos metadosPagamentos = metadosPagamentoService.update(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(metadosPagamentos);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar metado de pagamento: " + ex.getMessage());
        }
    }
    @DeleteMapping("/delete")
    @DefaultOperation(summary = "Deletar metado de pagamento", description = "Deleta um metado de pagamento", tags = {"Metado de pagamento"})
    public ResponseEntity<?> delete(@RequestBody DeleteMetadosPagamentosDTO dto) {
        try {
            MetadosPagamentos metadosPagamentos = metadosPagamentoService.deleteById(dto.id());
            if (metadosPagamentos == null || metadosPagamentos.getId() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(JsonUtil.createMessageJson("Metado de pagamento não encontrado", 404));
            }
            return ResponseEntity.status(HttpStatus.OK).body(JsonUtil.createMessageJson("Metado de pagamento deletado com sucesso", 200));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar metado de pagamento: " + ex.getMessage());
        }
    }
    @GetMapping("/findAll")
    @DefaultOperation(summary = "Buscar todos os metados de pagamento", description = "Busca todos os metados de pagamento", tags = {"Metado de pagamento"})
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(metadosPagamentoService.findAll());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JsonUtil.createMessageJson("Erro ao buscar todos os metados de pagamento: " + ex.getMessage(), 500));
        }
    }
}
