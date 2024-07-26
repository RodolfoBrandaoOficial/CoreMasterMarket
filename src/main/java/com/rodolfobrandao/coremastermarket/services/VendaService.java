package com.rodolfobrandao.coremastermarket.services;

import com.rodolfobrandao.coremastermarket.entities.Cliente;
import com.rodolfobrandao.coremastermarket.entities.pdv.Venda;
import com.rodolfobrandao.coremastermarket.repositories.VendaRepository;
import com.rodolfobrandao.coremastermarket.specifications.GenericSpecification;
import com.rodolfobrandao.coremastermarket.tools.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;

    @Autowired
    public VendaService(VendaRepository vendaRepository){
        this.vendaRepository = vendaRepository;
    }
    public Page<Venda> findAll(int page, String oper, int rp, String sortname, String sortorder) {
        Pageable pageable = PageRequest.of(page - 1,rp, Sort.by(Sort.Order.by(sortname).with(Sort.Direction.fromString(sortorder))));
        return vendaRepository.findAll(pageable);
    }
    public Venda create(Venda venda){
        Venda newVenda =  vendaRepository.save(venda);
        Optional<Venda> optVenda = vendaRepository.findById(newVenda.getId());
        return optVenda.get();
    }
    public Venda findById(Long id) {
        Optional<Venda> optVenda = vendaRepository.findById(id);
        return optVenda.orElse(null);
    }
    public Page<Cliente> findByQuery(String qtype, String query, String oper, int page, int rp, String sortname, String sortorder) {
        Pageable pageable = PageRequest.of(page - 1, rp, Sort.by(Sort.Order.by(sortname).with(Sort.Direction.fromString(sortorder))));
        Specification<Cliente> spec = GenericSpecification.buildSpecification(qtype, query, oper);
        return vendaRepository.findAll(spec, pageable);
    }
}
