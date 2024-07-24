package com.rodolfobrandao.coremastermarket.services;

import com.rodolfobrandao.coremastermarket.dtos.CreateClienteItemDTO;
import com.rodolfobrandao.coremastermarket.entities.Cliente;
import com.rodolfobrandao.coremastermarket.repositories.ClienteRepository;
import com.rodolfobrandao.coremastermarket.specifications.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Método que retorna todos os clientes
     *
     * @param qtype
     * @param query
     * @param oper
     * @param page
     * @param rp
     * @param sortname
     * @param sortorder
     * @return
     */
    public Page<Cliente> findByQuery(String qtype, String query, String oper, int page, int rp, String sortname, String sortorder) {
        Pageable pageable = PageRequest.of(page - 1, rp, Sort.by(Sort.Order.by(sortname).with(Sort.Direction.fromString(sortorder))));
        Specification<Cliente> spec = GenericSpecification.buildSpecification(qtype, query, oper);
        return clienteRepository.findAll(spec, pageable);
    }

    /**
     * Método que retorna todos os clientes
     *
     * @return
     */

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    /**
     * Método que retorna todos os clientes
     *
     * @return
     */
    public Cliente create(CreateClienteItemDTO dto) {
        Cliente cliente = new Cliente(
                dto.nome(),
                dto.dataNascimento(),
                dto.rua(),
                dto.bairro(),
                dto.numero(),
                dto.cpfCnpj(),
                dto.rgIe(),
                dto.telefone1(),
                dto.telefone2(),
                dto.emitirNota(),
                dto.email(),
                dto.cep(),
                dto.cidade(),
                dto.estado(),
                dto.dataCadastro(),
                dto.dataAlteracao(),
                dto.dataExclusao(),
                dto.ativo(),
                dto.observacao(),
                dto.limiteCredito(),
                dto.dataPagamento(),
                dto.dataVencimento()
        );
        return clienteRepository.save(cliente);
    }
}
