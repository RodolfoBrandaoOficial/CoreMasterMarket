package com.rodolfobrandao.coremastermarket.dtos.pdv;

import java.math.BigDecimal;

public record VendaItemDTO(
        Long id,
        BigDecimal quantidade,
        BigDecimal desconto,
        BigDecimal acrescimo,
        Long idProduto
) {
}
