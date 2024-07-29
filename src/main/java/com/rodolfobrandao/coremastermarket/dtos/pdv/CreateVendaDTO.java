package com.rodolfobrandao.coremastermarket.dtos.pdv;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CreateVendaDTO(
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraTermino,
        Long modoPagamento,
        String observacao,
        Long pdv,
        List<CreateVendaItemDTO> listVendaItens,
        Long idCliente
//        Long idModoPagamento
) {
    /**
     * Calcula o total da venda somando o valor de todos os itens.
     * @return O total da venda como um BigDecimal
     */
    public BigDecimal totalVenda() {
        return listVendaItens.stream()
                .map(item -> item.quantidade().multiply(item.precoUnitario())) // Use precoUnitario here
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
