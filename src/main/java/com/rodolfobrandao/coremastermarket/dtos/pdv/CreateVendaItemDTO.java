package com.rodolfobrandao.coremastermarket.dtos.pdv;

import java.math.BigDecimal;

public record CreateVendaItemDTO(
        BigDecimal quantidade,
        BigDecimal desconto,
        BigDecimal acrescimo,
        BigDecimal precoUnitario,
        Long idProduto,
        Long id_venda,
        Long id_cliente) {
    /**
     * CreateVendaItemDTO
     *  - quantidade: Quantidade do item
     *  - desconto: Desconto do item
     *  - acrescimo: Acréscimo do item
     *  - idProduto: Identificador do produto
     *  - id_venda: Identificador da venda
     */
}
