package com.rodolfobrandao.coremastermarket.services;

import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateProdutoDTO;
import com.rodolfobrandao.coremastermarket.dtos.pdv.UpdateProdutoDTO;
import com.rodolfobrandao.coremastermarket.entities.Cliente;
import com.rodolfobrandao.coremastermarket.entities.pdv.Marca;
import com.rodolfobrandao.coremastermarket.entities.pdv.Produto;
import com.rodolfobrandao.coremastermarket.repositories.MarcaRepository;
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
import java.time.LocalDateTime;

@Service
public class ProdutoService {
    final ProdutoRepository produtoRepository;
    private final MarcaRepository marcaRepository;
    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository, MarcaRepository marcaRepository) {
        this.produtoRepository = produtoRepository;
        this.marcaRepository = marcaRepository;
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
        LocalDateTime dataAtual = LocalDateTime.now();
        Marca marca = marcaRepository.findById(dto.marca())
                .orElseThrow(() -> new RuntimeException("Marca não encontrada"));
        Produto produto = new Produto(
                dto.precoVenda(),
                dto.codigoBarras(),
                dto.tipoEmbalagem(),
                dto.descricao(),
                dto.quantidade(),
                LocalDateTime.from(dto.criadoEm() == null ? dataAtual : dto.criadoEm()),
//              LocalDateTime.from(dto.atualizadoEm() == null ? dataAtual : dto.atualizadoEm()),
                marca,
                dto.ativo()
        );
        return produtoRepository.save(produto);
    }
    public Produto update(UpdateProdutoDTO dto) {
        LocalDate dataAtual = LocalDate.now();
        Produto produto = produtoRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        Marca marca = marcaRepository.findById(dto.marca())
                .orElseThrow(() -> new RuntimeException("Marca não encontrada"));
        produto.setPrecoVenda(dto.precoVenda());
        produto.setCodigoBarras(dto.codigoBarras());
        produto.setTipoEmbalagem(dto.tipoEmbalagem());
        produto.setDescricao(dto.descricao());
        produto.setQuantidade(dto.quantidade());
        produto.setAtualizadoEm(dto.atualizadoEm() == null ? dataAtual.atStartOfDay() : dto.atualizadoEm());
        produto.setMarca(marca);
        produto.setAtivo(dto.ativo());
        return produtoRepository.save(produto);
    }


}
