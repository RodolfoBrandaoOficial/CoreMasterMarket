package com.rodolfobrandao.coremastermarket.dtos.pdv;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateModoPagamentoDTO(
        BigDecimal limite,
        BigDecimal saldo,
        BigDecimal juros,
        Integer parcelas,
        LocalDateTime dataGerada,
        String tipoPagamento,
        String descricao
) {
}
