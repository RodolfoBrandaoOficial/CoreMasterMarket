package com.rodolfobrandao.coremastermarket.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
public class Cliente {
    /**
     * The id of the client.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 255)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(length = 255)
    private String rua;

    @Column(length = 255)
    private String bairro;

    @Column(length = 255)
    private String numero;

    @Column(length = 255)
    private String cpfCnpj;

    @Column(length = 255)
    private String rgIe;

    @Column(length = 255)
    private String telefone1;

    @Column(length = 255)
    private String telefone2;

    @Column(length = 255)
    private boolean emitirNota;

    @Column(length = 255)
    private String email;

    @Column(length = 255)
    private String cep;

    @Column(length = 255)
    private String cidade;

    @Column(length = 255)
    private String estado;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "data_alteracao", nullable = false)
    private LocalDateTime dataAlteracao;

    @Column(name = "data_exclusao")
    private LocalDateTime dataExclusao;

    @Column(length = 255)
    private boolean ativo;

    @Column(length = 255)
    private String observacao;

    @Column(length = 255)
    private double limiteCredito;

    @Column(name = "data_pagamento", nullable = false)
    private LocalDate dataPagamento;

    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "data_fechamento_fatura", nullable = false)
    private LocalDate dataFechamentoFatura;

    public Cliente(String nome, LocalDate dataNascimento, String rua, String bairro, String numero, String cpfCnpj, String rgIe, String telefone1, String telefone2, boolean emitirNota, String email, String cep, String cidade, String estado, LocalDateTime dataCadastro, LocalDateTime dataAlteracao, LocalDateTime dataExclusao, boolean ativo, String observacao, double limiteCredito, LocalDate dataPagamento, LocalDate dataVencimento, LocalDate dataFechamentoFatura) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.cpfCnpj = cpfCnpj;
        this.rgIe = rgIe;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.emitirNota = emitirNota;
        this.email = email;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.dataCadastro = LocalDateTime.from(dataCadastro);
        this.dataAlteracao = dataAlteracao;
        this.dataExclusao = dataExclusao;
        this.ativo = ativo;
        this.observacao = observacao;
        this.limiteCredito = limiteCredito;
        this.dataPagamento = dataPagamento;
        this.dataVencimento = dataVencimento;
        this.dataFechamentoFatura = dataFechamentoFatura;
    }

    public Cliente() {
    }

}
