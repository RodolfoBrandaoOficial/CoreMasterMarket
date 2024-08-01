package com.rodolfobrandao.coremastermarket.dtos.pdv;

import com.rodolfobrandao.coremastermarket.entities.pdv.ModoPagamento;
import com.rodolfobrandao.coremastermarket.entities.pdv.VendaItem;

import java.util.List;

public record UpdateVendaDTO(
        Long id,
        Long modoPagamentos,
        String observacao,
        List<VendaItem> listVendaItens
) {
}
/**
 * Classe que representa uma venda
 * @param id
 * @param modoPagamento
 * @param observacao
 * @param hash
 * @return UpdateVendaDTO
 * @since 1.0
 * @version 1.0
 */
