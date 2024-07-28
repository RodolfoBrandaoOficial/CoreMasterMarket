package com.rodolfobrandao.coremastermarket.entities.pdv;

import com.rodolfobrandao.coremastermarket.entities.Cliente;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
@Data
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "datahora_inicio", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataHoraInicio;

    @Column(name = "datahora_termino")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataHoraTermino;

    @Column(length = 255)
    private String observacao;

    private Long pdv;

    @Column(name = "hash_fiscal")
    @UuidGenerator
    private String hashfiscal;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_venda", referencedColumnName = "id")
    private List<VendaItem> listVendaItens;

    public Venda(LocalDateTime dataHoraInicio, LocalDateTime dataHoraTermino, String observacao, Long pdv, List<VendaItem> listVendaItens, Optional<Cliente> cliente) {
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraTermino = dataHoraTermino;
        this.observacao = observacao;
        this.pdv = pdv;
        this.listVendaItens = listVendaItens;
    }
    public Venda(Long id, LocalDateTime dataHoraInicio, LocalDateTime dataHoraTermino, String observacao, Long pdv, List<VendaItem> listVendaItens, Optional<Cliente> cliente, String hashfiscal) {
        this.id = id;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraTermino = dataHoraTermino;
        this.observacao = observacao;
        this.pdv = pdv;
        this.hashfiscal = hashfiscal;
        this.listVendaItens = listVendaItens;
    }

    public Venda() {

    }
}
