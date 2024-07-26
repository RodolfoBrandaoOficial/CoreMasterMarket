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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_venda", insertable = false, updatable = false)
    @JsonIgnore
    @JsonProperty("venda")
    private Venda venda;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_produto", insertable = false, updatable = false, referencedColumnName = "id")
    @JsonProperty("produto")
    private Produto produto;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cliente", insertable = false, updatable = false, referencedColumnName = "id")
    @JsonProperty("cliente")
    private Cliente cliente;

    public VendaItem(BigDecimal quantidade, BigDecimal desconto, BigDecimal acrescimo, Long idProduto) {
        this.quantidade = quantidade;
        this.acrescimo = acrescimo;
        this.desconto = desconto;
        this.idProduto = idProduto;
    }
    public VendaItem() {
    }
    public VendaItem(long l, BigDecimal bigDecimal, BigDecimal zero, BigDecimal zero1, Produto produto) {
    }

    public BigDecimal getTotal(int i) {
        return BigDecimal.valueOf(20.0);
    }

}
