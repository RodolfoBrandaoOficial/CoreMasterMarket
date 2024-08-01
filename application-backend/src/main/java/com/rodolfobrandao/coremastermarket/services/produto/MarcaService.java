package com.rodolfobrandao.coremastermarket.services.produto;

import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateMarcaDTO;
import com.rodolfobrandao.coremastermarket.entities.produto.Marca;
import com.rodolfobrandao.coremastermarket.repositories.produto.MarcaRepository;
import com.rodolfobrandao.coremastermarket.tools.JsonUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MarcaService {

    final MarcaRepository marcaRepository;

    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    public Page<Marca> findAll(int page, int size, String sortname, String sortorder) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(sortorder), sortname));
        return marcaRepository.findAll(pageRequest);
    }

    public Marca create(CreateMarcaDTO createMarcaDTO) {
        try {
            Marca marca = new Marca();
            marca.setNome(createMarcaDTO.nome());
            marca.setFabricante(createMarcaDTO.fabricante());
            return marcaRepository.save(marca);
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao criar marca: " + ex.getMessage());
        }
    }

    public Marca findById(Long id) {
        try {
            return marcaRepository.findById(id).orElse(null);
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao buscar marca: " + ex.getMessage());
        }
    }

    public ResponseEntity delete(Long id) {
        try {
            Marca marca = marcaRepository.findById(id).orElse(null);
            if (marca == null) {
                throw new RuntimeException("Marca não encontrada");
            }
            marcaRepository.delete(marca);
            return ResponseEntity.ok(JsonUtil.createMessageJson("Marca deletada com sucesso", HttpStatus.OK.value()));
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar marca: " + ex.getMessage());
        }
    }

    public Marca update(Long id, CreateMarcaDTO dto) {
        try {
            Marca marca = marcaRepository.findById(id).orElse(null);
            if (marca == null) {
                throw new RuntimeException("Marca não encontrada");
            }
            marca.setNome(dto.nome());
            marca.setFabricante(dto.fabricante());
            return marcaRepository.save(marca);
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar marca: " + ex.getMessage());
        }
    }

}
