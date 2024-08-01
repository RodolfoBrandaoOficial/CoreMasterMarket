package com.rodolfobrandao.coremastermarket.entities.pdv;

import com.rodolfobrandao.coremastermarket.entities.cliente.Cliente;
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

    @ManyToOne
    @JoinColumn(name = "id_venda", nullable = false)
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    // Construtor com idCliente removido
    public VendaItem(BigDecimal quantidade, BigDecimal desconto, BigDecimal acrescimo, Long idProduto, Venda venda, Cliente cliente) {
        this.quantidade = quantidade;
        this.desconto = desconto;
        this.acrescimo = acrescimo;
        this.idProduto = idProduto;
        this.venda = venda;
        this.cliente = cliente;
    }

    public VendaItem() {
    }

    public BigDecimal getTotal() {
        return BigDecimal.valueOf(20.0); // Valor de exemplo
    }

}
