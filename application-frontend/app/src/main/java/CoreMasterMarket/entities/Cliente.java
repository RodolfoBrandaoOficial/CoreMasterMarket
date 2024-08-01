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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nome;
    private double limiteCredito;
    private LocalDate dataFechamentoFatura;
    
    // Outras informações do cliente
    private String endereco;
    private String telefone;
    private String email;
    private LocalDate dataCadastro;
    private LocalDate dataAlteracao;
    private LocalDate dataExclusao;

}