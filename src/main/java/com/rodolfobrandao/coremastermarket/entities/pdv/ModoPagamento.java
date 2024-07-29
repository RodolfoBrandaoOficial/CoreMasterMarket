package com.rodolfobrandao.coremastermarket.entities.pdv;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ModoPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private BigDecimal limite;
    private BigDecimal saldo;
    private BigDecimal juros;
    private Integer parcelas;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataGerada;

    private String tipoPagamento; // Tipo de pagamento, por exemplo: "Boleto", "Cartão de Crédito", etc.
    private String descricao; // Descrição adicional ou observações

    // Relacionamento com a entidade Venda
    @OneToMany(mappedBy = "modoPagamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Venda> vendasPagas; // Lista de vendas associadas a esse modo de pagamento
}
