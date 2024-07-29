package com.rodolfobrandao.coremastermarket.services;

import com.rodolfobrandao.coremastermarket.entities.pdv.ModoPagamento;

import com.rodolfobrandao.coremastermarket.repositories.ModoPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModoPagamentoService {

    private final ModoPagamentoRepository modoPagamentoRepository;

    @Autowired
    public ModoPagamentoService(ModoPagamentoRepository modoPagamentoRepository) {
        this.modoPagamentoRepository = modoPagamentoRepository;
    }


    /**
     * Encontra um ModoPagamento pelo seu identificador.
     * @param id O identificador do ModoPagamento.
     * @return Um Optional contendo o ModoPagamento se encontrado, ou vazio se não encontrado.
     */
    public Optional<ModoPagamento> findById(Long id) {
        return modoPagamentoRepository.findById(id);
    }

    /**
     * Salva um ModoPagamento no banco de dados.
     * @param modoPagamento O ModoPagamento a ser salvo.
     * @return O ModoPagamento salvo.
     */
    public ModoPagamento save(ModoPagamento modoPagamento) {
        return modoPagamentoRepository.save(modoPagamento);
    }


    /**
     * Atualiza um ModoPagamento existente.
     * @param modoPagamento O ModoPagamento a ser atualizado.
     * @return O ModoPagamento atualizado.
     */
    public ModoPagamento update(ModoPagamento modoPagamento) {
        return modoPagamentoRepository.save(modoPagamento);
    }

    /**
     * Remove um ModoPagamento pelo seu identificador.
     * @param id O identificador do ModoPagamento a ser removido.
     */
    public void deleteById(Long id) {
        modoPagamentoRepository.deleteById(id);
    }
}
