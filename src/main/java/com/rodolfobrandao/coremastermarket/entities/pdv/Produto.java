package com.rodolfobrandao.coremastermarket.entities.pdv;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rodolfobrandao.coremastermarket.entities.Cliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private Long quantidade;

    @Column(name = "criado_em")//, nullable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")//, nullable = false)
    private LocalDateTime atualizadoEm;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_marca", insertable = false, updatable = false, referencedColumnName = "id")
    @JsonProperty("marca")
    private Marca marca;


    private boolean ativo;

    public Produto(BigDecimal precoVenda, String codigoBarras, String tipoEmbalagem, String descricao, Long quantidade, LocalDateTime criadoEm, LocalDateTime atualizadoEm, Marca marca, boolean ativo) {
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

    public Produto(BigDecimal precoVenda, String codigoBarras, String tipoEmbalagem, String descricao, Long quantidade, LocalDateTime criadoEm, Marca marca, boolean ativo) {
        this.precoVenda = precoVenda;
        this.codigoBarras = codigoBarras;
        this.tipoEmbalagem = tipoEmbalagem;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.criadoEm = criadoEm;
        this.marca = marca;
        this.ativo = ativo;
    }

    public Produto(BigDecimal precoVenda, String codigoBarras, String tipoEmbalagem, String descricao, Long quantidade, LocalDateTime criadoEm, LocalDateTime atualizadoEm, Long marca, boolean ativo) {
    }

    public Produto(BigDecimal precoVenda, String codigoBarras, String tipoEmbalagem, String descricao, Long quantidade, LocalDateTime criadoEm, LocalDateTime atualizadoEm, boolean ativo) {
    }
    public Produto(BigDecimal precoVenda, String codigoBarras, String tipoEmbalagem, String descricao, Long quantidade, LocalDateTime criadoEm, LocalDateTime atualizadoEm, Marca marca, boolean ativo, List<Cliente> clientes) {
    }
}
