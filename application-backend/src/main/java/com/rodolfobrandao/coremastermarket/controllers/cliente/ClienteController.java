package com.rodolfobrandao.coremastermarket.controllers.cliente;

import com.rodolfobrandao.coremastermarket.dtos.cliente.CreateClienteItemDTO;
import com.rodolfobrandao.coremastermarket.dtos.cliente.DeleteClienteDTO;
import com.rodolfobrandao.coremastermarket.dtos.cliente.SearchCriteriaDTO;
import com.rodolfobrandao.coremastermarket.entities.cliente.Cliente;
import com.rodolfobrandao.coremastermarket.exceptions.ErrorResponse;
import com.rodolfobrandao.coremastermarket.services.cliente.ClienteService;
import com.rodolfobrandao.coremastermarket.tools.CustomException;
import com.rodolfobrandao.coremastermarket.tools.JsonUtil;
import com.rodolfobrandao.coremastermarket.tools.swagger.DefaultOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * This method is used to list all clients.
     *
     * @return The page of clients.
     * @version 1.0
     * @since 1.0
     */
    @GetMapping("/listar")
    @DefaultOperation(summary = "Listar clientes", description = "Lista todos os clientes", tags = {"Cliente"})
    public ResponseEntity<?> listarClientes() {
        try {
            Page<Cliente> clientes = clienteService.findAll(1, ">=", 100000, "id", "asc");
            return ResponseEntity.ok(clientes);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("Erro ao listar clientes: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, System.currentTimeMillis(), 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * This method is used to disable ou enable a client.
     *
     * @param deleteClienteDTO The dto.
     * @return The client.
     * @throws Exception If the client cannot be updated.
     * @version 1.0
     * @since 1.0
     */

    @PutMapping("/enabled")
    @DefaultOperation(summary = "Habilitar cliente", description = "Habilita um cliente", tags = {"Cliente"})
    public ResponseEntity<?> habilitarCliente(@RequestBody DeleteClienteDTO deleteClienteDTO) {
        try {
            clienteService.findById(deleteClienteDTO.id());
            return ResponseEntity.ok(JsonUtil.createMessageJson("Cliente habilitado com sucesso", 200));
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("Erro ao habilitar cliente: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, System.currentTimeMillis(), 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * This method is used to search for a client by a query.
     *
     * @param searchCriteria The search criteria.
     * @return The page of clients.
     * @version 1.0
     * @since 1.0
     */

    @PostMapping("/findByQuery")
    @DefaultOperation(summary = "Pesquisar clientes", description = "Pesquisa por clientes", tags = {"Cliente"})
    public Page<Cliente> findByQuery(@RequestBody SearchCriteriaDTO searchCriteria) {
        try {
            return clienteService.findByQuery(
                    searchCriteria.qtype(),
                    searchCriteria.query(),
                    searchCriteria.oper(),
                    searchCriteria.page(),
                    searchCriteria.rp(),
                    searchCriteria.sortname(),
                    searchCriteria.sortorder()
            );
        } catch (Exception ex) {
            throw new RuntimeException(JsonUtil.createMessageJson("Erro ao listar clientes: " + ex.getMessage(), 500));
        }
    }

    /**
     * This method is used to search for a client by id.
     *
     * @param id The id.
     * @return The client.
     * @version 1.0
     * @since 1.0
     */
    @GetMapping("/buscar/{id}")
    @DefaultOperation(summary = "Pesquisar cliente por ID", description = "Pesquisa por cliente pelo ID", tags = {"Cliente"})
    public ResponseEntity<Cliente> buscarCliente(@PathVariable Long id) {
        if (id == null) {
            throw new CustomException("ID não pode ser nulo", HttpStatus.BAD_REQUEST);
        }
        if (id <= 0) {
            throw new CustomException("ID não pode ser menor ou igual a zero", HttpStatus.BAD_REQUEST);
        }
        Optional<Cliente> cliente = clienteService.findById(id);
        if (cliente.isEmpty()) {
            throw new CustomException("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cliente.get());
    }


    /**
     * This method is used to create a client.
     *
     * @param dto The dto.
     * @return The client.
     * @throws Exception If the client cannot be created.
     * @version 1.0
     * @since 1.0
     */
    @PostMapping("/create")
    @DefaultOperation(summary = "Criar cliente", description = "Cria um novo cliente", tags = {"Cliente"})
    public Cliente cadastrarCliente(@RequestBody CreateClienteItemDTO dto) {
        return clienteService.create(dto);
    }

    /**
     * This method is used to update a client.
     *
     * @param dto The dto.
     * @return The client.
     * @throws Exception If the client cannot be updated.
     * @version 1.0
     * @since 1.0
     */

    @PutMapping("/update")
    @DefaultOperation(summary = "Atualizar cliente", description = "Atualiza um cliente", tags = {"Cliente"})
    public Cliente atualizarCliente(@RequestBody CreateClienteItemDTO dto) {
        return clienteService.create(dto);
    }
}
