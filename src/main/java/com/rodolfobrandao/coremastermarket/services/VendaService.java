package com.rodolfobrandao.coremastermarket.services;

import com.rodolfobrandao.coremastermarket.entities.Cliente;
import com.rodolfobrandao.coremastermarket.entities.pdv.Venda;
import com.rodolfobrandao.coremastermarket.repositories.VendaRepository;
import com.rodolfobrandao.coremastermarket.specifications.GenericSpecification;
import com.rodolfobrandao.coremastermarket.tools.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;

    @Autowired
    public VendaService(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    public Page<Venda> findAll(int page, String oper, int rp, String sortname, String sortorder) {
        try {
            Pageable pageable = PageRequest.of(page - 1, rp, Sort.by(Sort.Order.by(sortname).with(Sort.Direction.fromString(sortorder))));
            return vendaRepository.findAll(pageable);
        } catch (Exception ex) {
            throw new CustomException("Erro ao listar vendas: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Venda create(Venda venda) {
        try {
            // Salva a venda
            Venda newVenda = vendaRepository.save(venda);
            // Verifica se a lista de itens é nula
            if (newVenda.getListVendaItens() != null) {
                // Associa os itens de venda à nova venda
                Venda finalNewVenda = newVenda;
                newVenda.getListVendaItens().forEach(item -> item.setVenda(finalNewVenda));
                // Atualiza e salva a venda com itens associados
                newVenda = vendaRepository.save(newVenda);
            } else {
                throw new CustomException("Erro ao criar venda: lista de itens de venda não pode ser nula", HttpStatus.BAD_REQUEST);
            }
            return newVenda;
        } catch (Exception ex) {
            throw new CustomException("Erro ao criar venda: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public Venda findById(Long id) {
        try {
            Optional<Venda> optVenda = vendaRepository.findById(id);
            return optVenda.orElseThrow(() -> new CustomException("Venda não encontrada", HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new CustomException("Erro ao buscar venda por ID: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Page<Cliente> findByQuery(String qtype, String query, String oper, int page, int rp, String sortname, String sortorder) {
        try {
            Pageable pageable = PageRequest.of(page - 1, rp, Sort.by(Sort.Order.by(sortname).with(Sort.Direction.fromString(sortorder))));
            Specification<Cliente> spec = GenericSpecification.buildSpecification(qtype, query, oper);
            return vendaRepository.findAll(spec, pageable);
        } catch (Exception ex) {
            throw new CustomException("Erro ao buscar vendas por consulta: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
