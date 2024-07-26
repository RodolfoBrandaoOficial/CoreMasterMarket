package com.rodolfobrandao.coremastermarket.controllers;

import com.rodolfobrandao.coremastermarket.dtos.CreateClienteItemDTO;
import com.rodolfobrandao.coremastermarket.dtos.SearchCriteriaDTO;
import com.rodolfobrandao.coremastermarket.entities.Cliente;
import com.rodolfobrandao.coremastermarket.exceptions.ErrorResponse;
import com.rodolfobrandao.coremastermarket.services.ClienteService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * This method is used to list all clients.
     *
     * @return The page of clients.
     * @since 1.0
     * @version 1.0
     */
    @GetMapping("/listar")
    public ResponseEntity<?> listarClientes() {
        try {
            Page<Cliente> clientes = clienteService.findAll(1, ">=", 1, "id", "asc");
            return ResponseEntity.ok(clientes);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("Erro ao listar clientes: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, System.currentTimeMillis(), 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * This method is used to search for a client by a query.
     *
     * @param searchCriteria The search criteria.
     * @return The page of clients.
     * @since 1.0
     * @version 1.0
     */

    @PostMapping("/findByQuery")
    public Page<Cliente> findByQuery(@RequestBody SearchCriteriaDTO searchCriteria) {
        return clienteService.findByQuery(
                searchCriteria.qtype(),
                searchCriteria.query(),
                searchCriteria.oper(),
                searchCriteria.page(),
                searchCriteria.rp(),
                searchCriteria.sortname(),
                searchCriteria.sortorder()
        );
    }

    /**
     * This method is used to search for a client by id.
     *
     * @param id The id.
     * @return The client.
     * @since 1.0
     * @version 1.0
     */
    @GetMapping("/buscar/{id}")
    public Cliente buscarCliente(@PathVariable Long id) {
        return clienteService.findById(id).orElse(null);
    }

    /**
     * This method is used to create a client.
     *
     * @param dto The dto.
     * @return The client.
     * @throws Exception If the client cannot be created.
     * @since 1.0
     * @version 1.0
     */
    @PostMapping("/create")
    public Cliente cadastrarCliente(@RequestBody CreateClienteItemDTO dto) {
        return clienteService.create(dto);
    }

    @PutMapping("/update")
    public Cliente atualizarCliente(@RequestBody CreateClienteItemDTO dto) {
        return clienteService.create(dto);
    }
}
