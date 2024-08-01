package com.rodolfobrandao.coremastermarket.dtos.pdv;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CreateModoPagamentoDTO(
        BigDecimal limite,
        BigDecimal saldo,
        BigDecimal juros,
        Integer parcelas,
        LocalDateTime dataGerada,
        List<Long> tiposPagamento,  // Alterado para List<Long>
        String descricao
){
    /**
     * CreateModoPagamentoDTO
     *  - limite: Limite do modo de pagamento
     *  - saldo: Saldo disponível
     *  - juros: Taxa de juros
     *  - parcelas: Número de parcelas
     *  - dataGerada: Data de criação do modo de pagamento
     *  - tipoPagamentoIds: Lista de IDs de tipo de pagamento (ex: "1", "2")
     *  - descricao: Descrição adicional
     */
}
