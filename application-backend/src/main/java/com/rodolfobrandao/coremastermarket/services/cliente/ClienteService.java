package com.rodolfobrandao.coremastermarket.services.cliente;

import com.rodolfobrandao.coremastermarket.dtos.cliente.CreateClienteItemDTO;
import com.rodolfobrandao.coremastermarket.dtos.cliente.UpdateClienteItemDTO;
import com.rodolfobrandao.coremastermarket.entities.cliente.Cliente;
import com.rodolfobrandao.coremastermarket.repositories.cliente.ClienteRepository;
import com.rodolfobrandao.coremastermarket.specifications.GenericSpecification;
import com.rodolfobrandao.coremastermarket.tools.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    LocalDateTime dataAtual = LocalDateTime.now();
    LocalDateTime dataAtualTime = LocalDateTime.now();

    /**
     * Retorna uma página de clientes com base nos parâmetros fornecidos.
     *
     * @param page      Número da página (1-indexado)
     * @param oper      Operador de filtragem
     * @param rp        Número de registros por página
     * @param sortname  Nome do campo para ordenação
     * @param sortorder Direção da ordenação (asc/desc)
     * @return Página de clientes
     */
    public Page<Cliente> findAll(int page, String oper, int rp, String sortname, String sortorder) {
        Pageable pageable = PageRequest.of(page - 1, rp, Sort.by(Sort.Order.by(sortname).with(Sort.Direction.fromString(sortorder))));
        return clienteRepository.findAll(pageable);
    }

    /**
     * Retorna uma página de clientes com base na consulta e filtros fornecidos.
     *
     * @param qtype     Tipo de consulta
     * @param query     Consulta a ser realizada
     * @param oper      Operador de filtragem
     * @param page      Número da página (1-indexado)
     * @param rp        Número de registros por página
     * @param sortname  Nome do campo para ordenação
     * @param sortorder Direção da ordenação (asc/desc)
     * @return Página de clientes filtrados
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
     * Retorna um cliente com base no ID fornecido.
     *
     * @param id ID do cliente
     * @return Cliente, se encontrado
     */
    public Optional<Cliente> findById(Long id) {
        try {
            return clienteRepository.findById(id);
        } catch (Exception ex) {
            throw new RuntimeException(JsonUtil.createMessageJson("Erro ao buscar cliente: " + ex.getMessage(), 500));
        }
    }

    /**
     * Desativa um cliente com base no ID fornecido.
     *
     * @param id ID do cliente a ser desativado
     * @return Cliente desativado
     */

    public Cliente enabledById(Long id) {
        try {
            ///se o cliente estiver ativo, desativa, e se estiver desativado ative
            Cliente cliente = clienteRepository.findById(id).get();
            if (cliente.isAtivo()) {
                cliente.setAtivo(false);
            } else {
                cliente.setAtivo(true);
            }
            return clienteRepository.save(cliente);
        } catch (Exception ex) {
            throw new RuntimeException(JsonUtil.createMessageJson("Erro ao desativar cliente: " + ex.getMessage(), 500));
        }
    }

    /**
     * Cria um novo cliente com base nos dados fornecidos.
     *
     * @param dto Dados do cliente a ser criado
     * @return Cliente criado
     */
    public Cliente create(CreateClienteItemDTO dto) {
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
                    dataAtualTime,
                    dto.dataAlteracao().atStartOfDay(),
                    dto.dataExclusao().atStartOfDay(),
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
     * Atualiza um cliente existente com base nos dados fornecidos.
     *
     * @param dto Dados atualizados do cliente
     * @return Cliente atualizado
     */
    public Cliente update(UpdateClienteItemDTO dto) {
        LocalDateTime dataAtual = LocalDateTime.now();
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
                dataAtual = LocalDateTime.now();
                clienteAtualizado.setDataAlteracao(dataAtual);
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
     * Deleta um cliente com base no ID fornecido.
     *
     * @param id ID do cliente a ser deletado
     */
    public void delete(Long id) {
        try {
            clienteRepository.deleteById(id);
        } catch (Exception ex) {
            throw new RuntimeException(JsonUtil.createMessageJson("Erro ao deletar cliente: " + ex.getMessage(), 500));
        }
    }
}
