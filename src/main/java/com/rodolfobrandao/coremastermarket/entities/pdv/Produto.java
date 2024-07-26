package com.rodolfobrandao.coremastermarket.entities.pdv;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_marca", referencedColumnName = "id")
    private List<Marca> marca;

    private boolean ativo;

    public Produto(BigDecimal precoVenda, String codigoBarras, String tipoEmbalagem, String descricao, String quantidade, LocalDate criadoEm, LocalDate atualizadoEm, List<Marca> marca, boolean ativo) {
        this.precoVenda = precoVenda;
        this.codigoBarras = codigoBarras;
        this.tipoEmbalagem = tipoEmbalagem;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
        this.marca = marca;
        this.ativo = ativo;
    }

    public Produto(BigDecimal precoVenda, String codigoBarras, String tipoEmbalagem, String descricao, String quantidade, LocalDate criadoEm, LocalDate atualizadoEm, Long marca, boolean ativo) {
    }
}
