package com.rodolfobrandao.coremastermarket.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CreateClienteItemDTO(String nome, LocalDate dataNascimento, String rua, String bairro, String numero,
                                   String cpfCnpj, String rgIe, String telefone1, String telefone2, boolean emitirNota,
                                   String email, String cep, String cidade, String estado, LocalDateTime dataCadastro,
                                   LocalDate dataAlteracao, LocalDate dataExclusao, boolean ativo, String observacao,
                                   double limiteCredito, LocalDate dataPagamento, LocalDate dataVencimento, LocalDate dataFechamentoFatura)
/**
 * Classe que representa um cliente
 * @param nome
 * @param dataNascimento
 * @param rua
 * @param bairro
 * @param numero
 * @param cpfCnpj
 * @param rgIe
 * @param telefone1
 * @param telefone2
 * @param emitirNota
 * @param email
 * @param cep
 * @param cidade
 * @param estado
 * @param dataCadastro
 * @param dataAlteracao
 * @param dataExclusao
 * @param ativo
 * @param observacao
 * @param limiteCredito
 * @param dataPagamento
 * @param dataVencimento
 * @param dataFechamentoFatura
 * @return CreateClienteItemDTO
 * @since 1.0
 * @version 1.0
 */
{
}

