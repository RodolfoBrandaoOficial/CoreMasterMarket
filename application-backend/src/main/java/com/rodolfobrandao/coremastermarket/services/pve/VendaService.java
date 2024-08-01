package com.rodolfobrandao.coremastermarket.services.pve;

import com.rodolfobrandao.coremastermarket.dtos.pdv.*;
import com.rodolfobrandao.coremastermarket.entities.cliente.Cliente;
import com.rodolfobrandao.coremastermarket.entities.pdv.ModoPagamento;
import com.rodolfobrandao.coremastermarket.entities.pdv.Venda;
import com.rodolfobrandao.coremastermarket.entities.pdv.VendaItem;
import com.rodolfobrandao.coremastermarket.entities.produto.Produto;
import com.rodolfobrandao.coremastermarket.repositories.pdv.VendaRepository;
import com.rodolfobrandao.coremastermarket.repositories.produto.ProdutoRepository;
import com.rodolfobrandao.coremastermarket.repositories.tools.GenericQueryService;
import com.rodolfobrandao.coremastermarket.services.produto.ProdutoService;
import com.rodolfobrandao.coremastermarket.tools.CustomException;
import com.rodolfobrandao.coremastermarket.tools.entities.PaginatedResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendaService {
    @PersistenceContext
    private EntityManager entityManager;

    // Repositório para acessar as vendas no banco de dados
    private final VendaRepository vendaRepository;
    // Serviço genérico para realizar consultas complexas
    private final ProdutoService produtoService;

    private final ProdutoRepository produtoRepository;
    private final GenericQueryService<Venda> genericQueryService;

    // Gerenciador de entidades para operações de persistência

    @Autowired
    public VendaService(VendaRepository vendaRepository, ProdutoService produtoService, ProdutoRepository produtoRepository, GenericQueryService<Venda> genericQueryService) {
        this.vendaRepository = vendaRepository;
        this.produtoService = produtoService;
        this.produtoRepository = produtoRepository;
        this.genericQueryService = genericQueryService;
    }

    /**
     * Recupera todas as vendas do repositório e as converte em DTOs.
     *
     * @return Lista de DTOs de venda
     */
    public List<VendaDTO> findAll() {
        return vendaRepository.findAll().stream()
                .map(this::convertToVendaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cria uma nova venda e salva no repositório.
     *
     * @param venda Objeto Venda a ser criado
     * @return Venda criada
     * @throws CustomException Se a lista de itens de venda for nula
     */
    public Venda create(Venda venda) {
        try {
            if (venda.getListVendaItens() == null) {
                throw new CustomException("Erro ao criar venda: lista de itens de venda não pode ser nula", HttpStatus.BAD_REQUEST);
            }

            Venda newVenda = vendaRepository.save(venda);
            newVenda.getListVendaItens().forEach(item -> item.setVenda(newVenda));
            return vendaRepository.save(newVenda);
        } catch (Exception ex) {
            throw new CustomException("Erro ao criar venda: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deleta uma venda existente pelo ID fornecido.
     * <p>
     *     Antes de deletar a venda, a quantidade de produtos vendidos é devolvida ao estoque.
     *     Se a venda não for encontrada, uma exceção é lançada.
     *     Se ocorrer um erro ao deletar a venda, uma exceção é lançada.
     *     Se ocorrer um erro ao devolver o estoque dos produtos, uma exceção é lançada.
     *     Se a venda for deletada com sucesso, nada é retornado.
     *     Se o ID da venda for encontrado, a venda é deletada com sucesso e a quantidade de produtos vendidos é devolvida ao estoque com sucesso.
     *  <p>
     *
     *
     */

    public void deleteVenda(Long id) {
        try {
            Optional<Venda> optVenda = vendaRepository.findById(id);
            if (optVenda.isEmpty()) {
                throw new CustomException("Venda não encontrada", HttpStatus.NOT_FOUND);
            }

            Venda venda = optVenda.get();

            // Devolver o estoque dos produtos
            for (VendaItem item : venda.getListVendaItens()) {
                produtoService.devolverEstoque(item.getIdProduto(), item.getQuantidade());
            }

            vendaRepository.delete(venda);
        } catch (Exception ex) {
            throw new CustomException("Erro ao deletar venda: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * Recupera uma venda específica pelo ID e a converte em DTO.
     *
     * @param id ID da venda a ser recuperada
     * @return DTO da venda
     * @throws CustomException Se a venda não for encontrada
     */
    public VendaDTO findById(Long id) {
        try {
            Optional<Venda> optVenda = vendaRepository.findById(id);
            if (optVenda.isEmpty()) {
                throw new CustomException("Venda não encontrada", HttpStatus.NOT_FOUND);
            }
            return convertToVendaDTO(optVenda.get());
        } catch (Exception ex) {
            throw new CustomException("Erro ao buscar venda por ID: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Atualiza uma venda com base no DTO fornecido.
     *
     * @param updateVendaDTO DTO com as informações de atualização
     * @return Venda atualizada
     * @throws CustomException Se a venda não for encontrada
     */
    public Venda update(UpdateVendaDTO updateVendaDTO) {
        try {
            Optional<Venda> optVenda = vendaRepository.findById(updateVendaDTO.id());
            if (optVenda.isEmpty()) {
                throw new CustomException("Venda não encontrada", HttpStatus.NOT_FOUND);
            }

            Venda venda = optVenda.get();
            venda.setObservacao(updateVendaDTO.observacao());
            venda.setModoPagamento(new ModoPagamento(updateVendaDTO.modoPagamentos()));
            return vendaRepository.save(venda);
        } catch (Exception ex) {
            throw new CustomException("Erro ao atualizar venda: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Converte uma entidade Venda em DTO de Venda.
     *
     * @param venda Entidade Venda a ser convertida
     * @return DTO da venda
     */
    public VendaDTO convertToVendaDTO(Venda venda) {
        if (venda == null) {
            return null; // ou lance uma exceção personalizada se necessário
        }

        String modoPagamentoStr = venda.getModoPagamento() != null
                ? venda.getModoPagamento().toString()
                : "Desconhecido";

        return new VendaDTO(
                venda.getId(),
                venda.getDataHoraInicio(),
                venda.getDataHoraTermino(),
                venda.getObservacao(),
                modoPagamentoStr,
                convertToClienteDTO(venda.getCliente()),
                venda.getListVendaItens() != null ? venda.getListVendaItens().stream()
                        .map(this::convertToVendaItemDTO)
                        .collect(Collectors.toList()) : Collections.emptyList()
        );
    }

    /**
     * Converte um item de venda em DTO de item de venda.
     *
     * @param item Entidade VendaItem a ser convertida
     * @return DTO do item de venda
     */
    private VendaItemDTO convertToVendaItemDTO(VendaItem item) {
        return new VendaItemDTO(
                item.getId(),
                item.getQuantidade(),
                item.getDesconto(),
                item.getAcrescimo(),
                item.getIdProduto()
        );
    }

    /**
     * Converte um cliente em DTO de cliente.
     *
     * @param cliente Entidade Cliente a ser convertida
     * @return DTO do cliente
     */
    private ClienteDTO convertToClienteDTO(Cliente cliente) {
        if (cliente == null) {
            return null; // ou lance uma exceção personalizada se necessário
        }
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpfCnpj()
        );
    }
}
