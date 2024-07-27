package com.rodolfobrandao.coremastermarket.services;

import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateMarcaDTO;
import com.rodolfobrandao.coremastermarket.entities.pdv.Marca;
import com.rodolfobrandao.coremastermarket.repositories.MarcaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MarcaService {

    final MarcaRepository marcaRepository;

    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    public Page<Marca> findAll(int page, String oper, int rp, String sortname, String sortorder) {
        Pageable pageable = PageRequest.of(page - 1,rp, Sort.by(Sort.Order.by(sortname).with(Sort.Direction.fromString(sortorder))));
        return marcaRepository.findAll(pageable);
    }

    public Marca create(CreateMarcaDTO dto) {
        Marca marca = new Marca();
        marca.setNome(dto.nome());
        marca.setFabricante(dto.fabricante());
        return marcaRepository.save(marca);
    }

    public Marca findById(Long id) {
        return marcaRepository.findById(id).orElse(null);
    }

}
