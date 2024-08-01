package com.rodolfobrandao.coremastermarket.repositories.produto;

import com.rodolfobrandao.coremastermarket.entities.produto.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    /**
     * Método que busca um produto pelo código de barras
     * @param codigoBarras
     * @return Produto encontrado ou vazio
     */
    Optional<Produto> findByCodigoBarras(String codigoBarras);

    /**
     * Método que busca produtos com especificações e paginação
     * @param spec Especificação de filtro
     * @param pageable Página e tamanho
     * @return Página de produtos
     */
    Page<Produto> findAll(Specification<Produto> spec, Pageable pageable);


}
