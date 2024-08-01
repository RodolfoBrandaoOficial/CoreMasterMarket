package com.rodolfobrandao.coremastermarket.dtos.cliente;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Classe que representa um cliente.
 * Esta classe é um DTO (Data Transfer Object) utilizado para a criação de um novo cliente.
 *
 * @param nome O nome do cliente.
 * @param dataNascimento A data de nascimento do cliente.
 * @param rua O nome da rua onde o cliente reside.
 * @param bairro O bairro onde o cliente reside.
 * @param numero O número da residência do cliente.
 * @param cpfCnpj O CPF ou CNPJ do cliente.
 * @param rgIe O RG ou IE (Inscrição Estadual) do cliente.
 * @param telefone1 O primeiro telefone de contato do cliente.
 * @param telefone2 O segundo telefone de contato do cliente.
 * @param emitirNota Indica se o cliente deseja que notas fiscais sejam emitidas para ele.
 * @param email O endereço de e-mail do cliente.
 * @param cep O CEP da residência do cliente.
 * @param cidade A cidade onde o cliente reside.
 * @param estado O estado onde o cliente reside.
 * @param dataCadastro A data de cadastro do cliente.
 * @param dataAlteracao A data da última alteração dos dados do cliente.
 * @param dataExclusao A data em que o cliente foi excluído, se aplicável.
 * @param ativo Indica se o cliente está ativo.
 * @param observacao Observações adicionais sobre o cliente.
 * @param limiteCredito O limite de crédito disponível para o cliente.
 * @param dataPagamento A data do próximo pagamento do cliente.
 * @param dataVencimento A data de vencimento do próximo pagamento do cliente.
 * @param dataFechamentoFatura A data de fechamento da fatura do cliente.
 *
 * @since 1.0
 * @version 1.0
 */
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
        LocalDateTime dataCadastro,
        LocalDate dataAlteracao,
        LocalDate dataExclusao,
        boolean ativo,
        String observacao,
        double limiteCredito,
        LocalDate dataPagamento,
        LocalDate dataVencimento,
        LocalDate dataFechamentoFatura
) {
}
