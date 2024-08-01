package com.rodolfobrandao.coremastermarket.repositories.pdv;

import com.rodolfobrandao.coremastermarket.entities.pdv.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    //crie uma busca por paginacao
    

}
