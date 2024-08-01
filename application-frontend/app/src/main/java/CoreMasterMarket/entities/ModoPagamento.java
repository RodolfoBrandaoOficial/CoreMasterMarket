/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoreMasterMarket.entities;

/**
 *
 * @author rodolfobrandao
 */
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private BigDecimal limite;
    private BigDecimal saldo;
    private BigDecimal juros;
    private Integer parcelas;
    private LocalDateTime dataGerada;

    // Relacionamento com a entidade Pedido
    @OneToMany(mappedBy = "modoPagamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos;

}