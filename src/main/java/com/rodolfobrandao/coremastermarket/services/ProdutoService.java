package com.rodolfobrandao.coremastermarket.services;

import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateProdutoDTO;
import com.rodolfobrandao.coremastermarket.entities.Cliente;
import com.rodolfobrandao.coremastermarket.entities.pdv.Produto;
import com.rodolfobrandao.coremastermarket.repositories.ProdutoRepository;
import com.rodolfobrandao.coremastermarket.specifications.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProdutoService {
    final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }
    public Page<Produto> findAll(int page, String oper, int rp, String sortname, String sortorder) {
        Pageable pageable = PageRequest.of(page - 1,rp, Sort.by(Sort.Order.by(sortname).with(Sort.Direction.fromString(sortorder))));
        return produtoRepository.findAll(pageable);
    }
    public Page<Cliente> findByQuery(String qtype, String query, String oper, int page, int rp, String sortname, String sortorder) {
        Pageable pageable = PageRequest.of(page - 1, rp, Sort.by(Sort.Order.by(sortname).with(Sort.Direction.fromString(sortorder))));
        Specification<Cliente> spec = GenericSpecification.buildSpecification(qtype, query, oper);
        return produtoRepository.findAll(spec, pageable);
    }
    public Produto create(CreateProdutoDTO dto) {
        LocalDate dataAtual = LocalDate.now();
        Produto produto = new Produto(
                dto.precoVenda(),
                dto.codigoBarras(),
                dto.tipoEmbalagem(),
                dto.descricao(),
                dto.quantidade(),
                dto.criadoEm() == null ? dataAtual : dto.criadoEm(),
                dto.atualizadoEm() == null ? dataAtual : dto.atualizadoEm(),
                dto.marca(),
                dto.ativo()
        );
        return produtoRepository.save(produto);
    }
    public Produto update(Produto produto) {
        return produtoRepository.save(produto);
    }


}
