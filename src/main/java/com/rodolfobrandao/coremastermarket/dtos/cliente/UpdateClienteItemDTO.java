package com.rodolfobrandao.coremastermarket.dtos.cliente;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UpdateClienteItemDTO(Long id, String nome, LocalDate dataNascimento, String rua, String bairro, String numero,
                                   String cpfCnpj, String rgIe, String telefone1, String telefone2, boolean emitirNota,
                                   String email, String cep, String cidade, String estado, LocalDateTime dataCadastro,
                                   LocalDate dataAlteracao, LocalDate dataExclusao, boolean ativo, String observacao,
                                   Double limiteCredito, LocalDate dataPagamento, LocalDate dataVencimento, LocalDate dataFechamentoFatura) {
    /**
     * UpdateClienteItemDTO
     * @param id Como base para atualizar
     */
}
