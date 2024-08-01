package com.rodolfobrandao.coremastermarket.services.pve;

import com.rodolfobrandao.coremastermarket.entities.pdv.MetadosPagamentos;
import com.rodolfobrandao.coremastermarket.entities.pdv.ModoPagamento;
import com.rodolfobrandao.coremastermarket.repositories.pdv.MetadosPagamentosRepository;
import com.rodolfobrandao.coremastermarket.repositories.pdv.ModoPagamentoRepository;
import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateModoPagamentoDTO;
import com.rodolfobrandao.coremastermarket.dtos.pdv.UpdateModoPagamentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela gestão dos modos de pagamento.
 * Fornece métodos para criar, atualizar, encontrar e remover modos de pagamento.
 */
@Service
public class ModoPagamentoService {

    private final ModoPagamentoRepository modoPagamentoRepository;
    private final MetadosPagamentosRepository metadosPagamentosRepository;

    /**
     * Construtor da classe ModoPagamentoService.
     * @param modoPagamentoRepository Repositório para operações CRUD com a entidade ModoPagamento.
     * @param metadosPagamentosRepository Repositório para operações CRUD com a entidade MetadosPagamentos.
     */
    @Autowired
    public ModoPagamentoService(ModoPagamentoRepository modoPagamentoRepository, MetadosPagamentosRepository metadosPagamentosRepository) {
        this.modoPagamentoRepository = modoPagamentoRepository;
        this.metadosPagamentosRepository = metadosPagamentosRepository;
    }

    /**
     * Encontra um ModoPagamento pelo seu identificador.
     * @param id O identificador do ModoPagamento.
     * @return Um Optional contendo o ModoPagamento se encontrado, ou vazio se não encontrado.
     */
    public Optional<ModoPagamento> findById(Long id) {
        return modoPagamentoRepository.findById(id);
    }

    /**
     * Retorna todos os modos de pagamento.
     * @return Lista de modos de pagamento.
     */
    public List<ModoPagamento> findAll() {
        return modoPagamentoRepository.findAll();
    }

    /**
     * Salva um ModoPagamento no banco de dados.
     * @param modoPagamento O ModoPagamento a ser salvo.
     * @return O ModoPagamento salvo.
     */
    public ModoPagamento save(ModoPagamento modoPagamento) {
        return modoPagamentoRepository.save(modoPagamento);
    }

    /**
     * Cria um novo ModoPagamento baseado no DTO fornecido.
     * @param dto O DTO contendo os dados para criação do ModoPagamento.
     * @return O ModoPagamento criado.
     */
    public ModoPagamento create(CreateModoPagamentoDTO dto) {
        // Encontre todos os tipos de pagamento com base na lista de IDs fornecidos
        List<MetadosPagamentos> tiposPagamento = metadosPagamentosRepository.findAllById(dto.tiposPagamento());

        // Crie um novo objeto ModoPagamento
        ModoPagamento modoPagamento = new ModoPagamento();
        modoPagamento.setLimite(dto.limite());
        modoPagamento.setSaldo(dto.saldo());
        modoPagamento.setJuros(dto.juros());
        modoPagamento.setParcelas(dto.parcelas());
        modoPagamento.setDataGerada(dto.dataGerada());

        // Associe o primeiro tipo de pagamento encontrado
        if (!tiposPagamento.isEmpty()) {
            modoPagamento.setTipoPagamento(tiposPagamento.get(0));
        } else {
            throw new IllegalArgumentException("Nenhum tipo de pagamento encontrado para os IDs fornecidos.");
        }

        modoPagamento.setDescricao(dto.descricao());

        // Salve e retorne o modo de pagamento
        return modoPagamentoRepository.save(modoPagamento);
    }

    /**
     * Atualiza um ModoPagamento existente baseado no DTO fornecido.
     * @param id O identificador do ModoPagamento a ser atualizado.
     * @param dto O DTO contendo os dados para atualização do ModoPagamento.
     * @return O ModoPagamento atualizado.
     */
    public ModoPagamento update(Long id, UpdateModoPagamentoDTO dto) {
        // Encontre o ModoPagamento existente
        ModoPagamento modoPagamento = modoPagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Modo de pagamento não encontrado"));

        // Atualize os campos do ModoPagamento
        modoPagamento.setLimite(dto.limite());
        modoPagamento.setSaldo(dto.saldo());
        modoPagamento.setJuros(dto.juros());
        modoPagamento.setParcelas(dto.parcelas());
        modoPagamento.setDataGerada(dto.dataGerada());
        modoPagamento.setDescricao(dto.descricao());

        // Atualize o tipo de pagamento
        List<MetadosPagamentos> tiposPagamento = metadosPagamentosRepository.findAllById(dto.tipoPagamentoIds());
        if (!tiposPagamento.isEmpty()) {
            // Assumindo que você queira definir o primeiro tipo de pagamento encontrado
            modoPagamento.setTipoPagamento(tiposPagamento.get(0));
        } else {
            throw new IllegalArgumentException("Nenhum tipo de pagamento encontrado para os IDs fornecidos.");
        }

        // Salve e retorne o ModoPagamento atualizado
        return modoPagamentoRepository.save(modoPagamento);
    }

    /**
     * Remove um ModoPagamento pelo seu identificador.
     * @param id O identificador do ModoPagamento a ser removido.
     */
    public void deleteById(Long id) {
        modoPagamentoRepository.deleteById(id);
    }
}
