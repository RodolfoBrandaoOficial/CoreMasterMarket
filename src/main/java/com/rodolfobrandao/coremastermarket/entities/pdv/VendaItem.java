package com.rodolfobrandao.coremastermarket.entities.pdv;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rodolfobrandao.coremastermarket.entities.Cliente;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class VendaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private BigDecimal quantidade;
    private BigDecimal desconto;
    private BigDecimal acrescimo;

    @Column(name = "id_produto")
    private Long idProduto;

    @Column(name = "id_venda")
    @JsonIgnore
    private Long idVenda;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_venda", insertable = false, updatable = false)
    @JsonIgnore
    @JsonProperty("venda")
    private Venda venda;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_produto", insertable = false, updatable = false, referencedColumnName = "id")
    @JsonProperty("produto")
    private Produto produto;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_cliente")
    @JsonProperty("cliente")
    private Cliente cliente;

    // Construtor com idCliente removido
    public VendaItem(BigDecimal quantidade, BigDecimal desconto, BigDecimal acrescimo, Long idProduto, Cliente cliente) {
        this.quantidade = quantidade;
        this.acrescimo = acrescimo;
        this.desconto = desconto;
        this.idProduto = idProduto;
        this.cliente = cliente; // Associar o cliente diretamente
    }

    public VendaItem() {
    }

    public BigDecimal getTotal() {
        return BigDecimal.valueOf(20.0);
    }
}
