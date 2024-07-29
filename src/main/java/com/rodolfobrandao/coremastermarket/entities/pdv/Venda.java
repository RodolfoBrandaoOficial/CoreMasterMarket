package com.rodolfobrandao.coremastermarket.entities.pdv;

import com.rodolfobrandao.coremastermarket.entities.Cliente;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "datahora_inicio")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataHoraInicio;

    @Column(name = "datahora_termino")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataHoraTermino;

    @Column(length = 255)
    private String observacao;

    private Long pdv;

    @Column(unique = true)
    private String hash;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "venda", orphanRemoval = true)
    private List<VendaItem> listVendaItens = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "modo_pagamento_id", referencedColumnName = "id")
    private ModoPagamento modoPagamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Venda(LocalDateTime dataHoraInicio, LocalDateTime dataHoraTermino, String observacao, Long pdv, List<VendaItem> listVendaItens, ModoPagamento modoPagamento, Cliente cliente) {
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraTermino = dataHoraTermino;
        this.observacao = observacao;
        this.pdv = pdv;
        this.listVendaItens = listVendaItens != null ? listVendaItens : new ArrayList<>();
        this.modoPagamento = modoPagamento;
        this.cliente = cliente;
        this.hash = UUID.randomUUID().toString();
    }

    public Venda() {
        this.listVendaItens = new ArrayList<>();
        this.hash = UUID.randomUUID().toString();
    }

    public Venda(LocalDateTime dataHoraInicio, LocalDateTime dataHoraTermino, String observacao, Long pdv, List<VendaItem> listItens, Long aLong, Cliente cliente) {
    this.dataHoraInicio = dataHoraInicio;
    this.dataHoraTermino = dataHoraTermino;
    this.observacao = observacao;
    this.pdv = pdv;
    this.listVendaItens = listItens;
    this.cliente = cliente;

    }

    // Outros construtores e métodos
}
