package com.rodolfobrandao.coremastermarket.entities.pdv;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produto implements Serializable {
    /**
     * Identificador do produto
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private BigDecimal precoVenda;

    @Column(unique = true)
    private String codigoBarras;

    private String tipoEmbalagem;
    private String descricao;

    private String quantidade;

    @Column(name = "criado_em", nullable = false)
    private LocalDate criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDate atualizadoEm;

    public Produto(String codigoBarras, String descricao, BigDecimal precoVenda, String tipoEmbalagem, String quantidade, LocalDate criadoEm, LocalDate atualizadoEm){
        this.codigoBarras = codigoBarras;
        this.descricao = descricao;
        this.precoVenda = precoVenda;
        this.tipoEmbalagem = tipoEmbalagem;
        this.quantidade = quantidade;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }

}
