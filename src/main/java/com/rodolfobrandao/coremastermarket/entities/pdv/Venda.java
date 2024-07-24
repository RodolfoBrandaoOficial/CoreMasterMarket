package com.rodolfobrandao.coremastermarket.entities.pdv;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.List;

@Entity
@Data
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "datahora_inicio", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataHoraInicio;

    @Column(name = "datahora_termino")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataHoraTermino;

    @Column(length = 255)
    private String observacao;

    private Long pdv;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_venda", referencedColumnName = "id")
    private List<VendaItem> listVendaItens;

    public Venda(Date dataHoraInicio, Date dataHoraTermino, String observacao, Long pdv, List<VendaItem> listVendaItens) {
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraTermino = dataHoraTermino;
        this.observacao = observacao;
        this.pdv = pdv;
        this.listVendaItens = listVendaItens;
    }

    public Venda() {

    }
}
