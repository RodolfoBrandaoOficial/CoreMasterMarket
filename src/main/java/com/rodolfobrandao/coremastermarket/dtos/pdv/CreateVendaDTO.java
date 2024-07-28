package com.rodolfobrandao.coremastermarket.dtos.pdv;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CreateVendaDTO(Long id, LocalDateTime dataHoraInicio, LocalDateTime dataHoraTermino, String observacao,
                             Long pdv, List<CreateVendaItemDTO> listVendaItens, Long idCliente, String hashfiscal) {
/**
 * CreateVendaDTO
 * @param id
 * @param dataHoraInicio
 * @param dataHoraTermino
 * @param observacao
 * @param pdv
 * @param listVendaItens
 * @param idCliente
 * @param hashfiscal
 * @return CreateVendaDTO
 * @since 1.0
 * @version 1.0
 */
}
