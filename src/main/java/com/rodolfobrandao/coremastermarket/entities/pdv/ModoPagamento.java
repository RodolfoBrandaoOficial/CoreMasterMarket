package com.rodolfobrandao.coremastermarket.entities.pdv;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ModoPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private BigDecimal limite;
    private BigDecimal saldo;
    private BigDecimal juros;
    private Integer parcelas;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataGerada;

    @ManyToOne
    @JoinColumn(name = "tipo_pagamento_id", referencedColumnName = "id")
    private MetadosPagamentos tipoPagamento;

    private String descricao; // Descrição adicional ou observações

    // Relacionamento com a entidade Venda
    @OneToMany(mappedBy = "modoPagamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Venda> vendasPagas; // Lista de vendas associadas a esse modo de pagamento

    public ModoPagamento(Long aLong) {
        this.id = aLong;
        this.limite = limite;
        this.saldo = saldo;
        this.juros = juros;
        this.parcelas = parcelas;
        this.dataGerada = dataGerada;
        this.tipoPagamento = tipoPagamento;
        this.descricao = descricao;

    }

}
