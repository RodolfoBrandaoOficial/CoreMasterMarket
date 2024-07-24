package com.rodolfobrandao.coremastermarket.dtos.pdv;

import com.rodolfobrandao.coremastermarket.entities.pdv.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
}
