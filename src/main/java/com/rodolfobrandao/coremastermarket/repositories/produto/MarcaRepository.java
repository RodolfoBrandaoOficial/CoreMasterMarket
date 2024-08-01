package com.rodolfobrandao.coremastermarket.repositories.produto;

import com.rodolfobrandao.coremastermarket.entities.produto.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
}
