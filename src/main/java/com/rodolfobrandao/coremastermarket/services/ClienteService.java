package com.rodolfobrandao.coremastermarket.services;

import com.rodolfobrandao.coremastermarket.dtos.CreateClienteItemDTO;
import com.rodolfobrandao.coremastermarket.dtos.UpdateClienteItemDTO;
import com.rodolfobrandao.coremastermarket.entities.Cliente;
import com.rodolfobrandao.coremastermarket.repositories.ClienteRepository;
import com.rodolfobrandao.coremastermarket.specifications.GenericSpecification;
import com.rodolfobrandao.coremastermarket.tools.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        try {
            this.clienteRepository = clienteRepository;
        } catch (Exception ex) {
            throw new RuntimeException(JsonUtil.createMessageJson("Erro ao instanciar ClienteService: " + ex.getMessage(), 500));
        }
    }

    public Page<Cliente> findAll(int page, String oper, int rp, String sortname, String sortorder) {
        Pageable pageable = PageRequest.of(page - 1, rp, Sort.by(Sort.Order.by(sortname).with(Sort.Direction.fromString(sortorder))));
        return clienteRepository.findAll(pageable);
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
        try {
            Pageable pageable = PageRequest.of(page - 1, rp, Sort.by(Sort.Order.by(sortname).with(Sort.Direction.fromString(sortorder))));
            Specification<Cliente> spec = GenericSpecification.buildSpecification(qtype, query, oper);
            return clienteRepository.findAll(spec, pageable);
        } catch (Exception ex) {
            throw new RuntimeException(JsonUtil.createMessageJson("Erro ao listar clientes: " + ex.getMessage(), 500));
        }
    }

    /**
     * Método que retorna todos os clientes
     *
     * @return
     */

    public Optional<Cliente> findById(Long id) {
        try {
            return clienteRepository.findById(id);
        } catch (Exception ex) {
            throw new RuntimeException(JsonUtil.createMessageJson("Erro ao listar clientes: " + ex.getMessage(), 500));
        }
    }

    /**
     * Método que retorna todos os clientes
     *
     * @return
     */
    public Cliente create(CreateClienteItemDTO dto) {
        LocalDate DataAtual = LocalDate.now();
        try {
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
                    DataAtual, //dataCadastro
                    dto.dataAlteracao(),
                    dto.dataExclusao(),
                    dto.ativo(),
                    dto.observacao(),
                    dto.limiteCredito(),
                    dto.dataPagamento(),
                    dto.dataVencimento(),
                    dto.dataFechamentoFatura()
            );
            return clienteRepository.save(cliente);
        } catch (Exception ex) {
            throw new RuntimeException(JsonUtil.createMessageJson("Erro ao gravar cliente: " + ex.getMessage(), 500));
        }
    }

    /**
     * Método que atualiza cliente
     *
     * @param dto
     */
    public Cliente update(UpdateClienteItemDTO dto) {
        LocalDate DataAtual = LocalDate.now();
        Optional<Cliente> cliente = clienteRepository.findById(dto.id());
        try {
            if (cliente.isPresent()) {
                Cliente clienteAtualizado = cliente.get();
                clienteAtualizado.setNome(dto.nome());
                clienteAtualizado.setDataNascimento(dto.dataNascimento());
                clienteAtualizado.setRua(dto.rua());
                clienteAtualizado.setBairro(dto.bairro());
                clienteAtualizado.setNumero(dto.numero());
                clienteAtualizado.setCpfCnpj(dto.cpfCnpj());
                clienteAtualizado.setRgIe(dto.rgIe());
                clienteAtualizado.setTelefone1(dto.telefone1());
                clienteAtualizado.setTelefone2(dto.telefone2());
                clienteAtualizado.setEmitirNota(dto.emitirNota());
                clienteAtualizado.setEmail(dto.email());
                clienteAtualizado.setCep(dto.cep());
                clienteAtualizado.setCidade(dto.cidade());
                clienteAtualizado.setEstado(dto.estado());
                clienteAtualizado.setDataAlteracao(DataAtual);
                clienteAtualizado.setDataExclusao(dto.dataExclusao());
                clienteAtualizado.setAtivo(dto.ativo());
                clienteAtualizado.setObservacao(dto.observacao());
                clienteAtualizado.setLimiteCredito(dto.limiteCredito());
                clienteAtualizado.setDataPagamento(dto.dataPagamento());
                clienteAtualizado.setDataVencimento(dto.dataVencimento());
                clienteAtualizado.setDataFechamentoFatura(dto.dataFechamentoFatura());
                return clienteRepository.save(clienteAtualizado);
            } else {
                throw new RuntimeException(JsonUtil.createMessageJson("Cliente não encontrado", 404));
            }
        } catch (Exception ex) {
            throw new RuntimeException(JsonUtil.createMessageJson("Erro ao atualizar cliente: " + ex.getMessage(), 500));
        }
    }

    /**
     * Método que deleta cliente
     *
     * @param id
     */
    public void delete(Long id) {
        try {
            clienteRepository.deleteById(id);
        } catch (Exception ex) {
            throw new RuntimeException(JsonUtil.createMessageJson("Erro ao deletar cliente: " + ex.getMessage(), 500));
        }
    }
}
