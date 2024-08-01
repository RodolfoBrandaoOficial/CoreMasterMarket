package com.rodolfobrandao.coremastermarket.dtos.pdv;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record UpdateModoPagamentoDTO(
        Long id,
        BigDecimal limite,
        BigDecimal saldo,
        BigDecimal juros,
        Integer parcelas,
        LocalDateTime dataGerada,
        List<Long> tipoPagamentoIds,
        String descricao)
{

}
