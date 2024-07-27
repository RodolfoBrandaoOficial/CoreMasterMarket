package com.rodolfobrandao.coremastermarket.dtos.pdv;

import com.rodolfobrandao.coremastermarket.entities.pdv.VendaItem;

import java.time.LocalDateTime; // Utilize LocalDateTime para datas e horas
import java.util.List;

public record CreateVendaDTO(Long id, LocalDateTime dataHoraInicio, LocalDateTime dataHoraTermino, String observacao, Long pdv, List<CreateVendaItemDTO> listVendaItens, Long idCliente) {
/**
     * CreateVendaDTO
     * @param id
     * @param dataHoraInicio
     * @param dataHoraTermino
     * @param observacao
     * @param pdv
     * @param listVendaItens
     * @param idCliente
     * @return CreateVendaDTO
     * @since 1.0
     * @version 1.0
     */
}
