package com.rodolfobrandao.coremastermarket.dtos;

import java.time.LocalDate;

public record CreateClienteItemDTO(
        String nome,
        LocalDate dataNascimento,
        String rua,
        String bairro,
        String numero,
        String cpfCnpj,
        String rgIe,
        String telefone1,
        String telefone2,
        boolean emitirNota,
        String email,
        String cep,
        String cidade,
        String estado,
        LocalDate dataCadastro,
        LocalDate dataAlteracao,
        LocalDate dataExclusao,
        boolean ativo,
        String observacao,
        double limiteCredito,
        LocalDate dataPagamento,
        LocalDate dataVencimento
)
/**
 * Classe que representa um cliente
 */
{
}

