package com.rodolfobrandao.coremastermarket.dtos.pdv;

import java.time.LocalDateTime;
import java.util.List;

public record VendaDTO(
        Long id,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraTermino,
        String observacao,
        String modoPagamento,
        ClienteDTO cliente,
        List<VendaItemDTO> listVendaItens
) {
}
