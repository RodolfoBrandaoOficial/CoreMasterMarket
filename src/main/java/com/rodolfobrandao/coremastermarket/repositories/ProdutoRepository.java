package com.rodolfobrandao.coremastermarket.repositories;

import com.rodolfobrandao.coremastermarket.entities.pdv.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    //Consulta por código de barras
    /**
     * Método que busca um produto pelo código de barras
     * @param codigoBarras
     * @return
     */
    Optional<Produto> findByCodigoBarras(String codigoBarras);
}
