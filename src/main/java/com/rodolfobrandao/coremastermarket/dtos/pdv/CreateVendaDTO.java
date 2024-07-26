package com.rodolfobrandao.coremastermarket.dtos.pdv;

import com.rodolfobrandao.coremastermarket.entities.pdv.VendaItem;

import java.sql.Date;
import java.util.List;

public record CreateVendaDTO(Long id, Date dataHoraInicio, Date dataHoraTermino, String observacao, Long pdv,
                             List<VendaItem> listVendaItens) {
    /**
     * CreateVendaDTO
     * @param id
     * @param dataHoraInicio
     * @param dataHoraTermino
     * @param observacao
     * @param pdv
     * @param listVendaItens
     * @return CreateVendaDTO
     * @since 1.0
     * @version 1.0
     */
}
