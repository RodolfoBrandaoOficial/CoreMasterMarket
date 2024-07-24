package com.rodolfobrandao.coremastermarket.controllers;

import com.rodolfobrandao.coremastermarket.dtos.CreateClienteItemDTO;
import com.rodolfobrandao.coremastermarket.dtos.SearchCriteriaDTO;
import com.rodolfobrandao.coremastermarket.entities.Cliente;
import com.rodolfobrandao.coremastermarket.services.ClienteService;
import org.springframework.data.domain.Page;
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
     * @param qtype     The query type.
     * @param query     The query.
     * @param oper      The operation.
     * @param page      The page.
     * @param rp        The rp.
     * @param sortname  The sort name.
     * @param sortorder The sort order.
     * @return The page of clients.
     */
    @GetMapping("/listar")
    public Page<Cliente> listarClientes(
            @RequestParam(value = "qtype", required = false) String qtype,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "oper", required = false) String oper,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "rp", defaultValue = "10") int rp,
            @RequestParam(value = "sortname", defaultValue = "id") String sortname,
            @RequestParam(value = "sortorder", defaultValue = "asc") String sortorder) {

        return clienteService.findByQuery(qtype, query, oper, page, rp, sortname, sortorder);
    }

    /**
     * This method is used to search for a client by a query.
     *
     * @param searchCriteria The search criteria.
     * @return The page of clients.
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
     */
    @PostMapping("/cadastrar")
    public Cliente cadastrarCliente(@RequestBody CreateClienteItemDTO dto) {
        return clienteService.create(dto);
    }
}
