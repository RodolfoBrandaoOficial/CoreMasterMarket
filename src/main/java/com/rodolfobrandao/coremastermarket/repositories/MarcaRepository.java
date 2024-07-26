package com.rodolfobrandao.coremastermarket.repositories;

import com.rodolfobrandao.coremastermarket.entities.pdv.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
}
