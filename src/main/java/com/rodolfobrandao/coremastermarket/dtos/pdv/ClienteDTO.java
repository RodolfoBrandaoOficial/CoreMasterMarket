package com.rodolfobrandao.coremastermarket.dtos.pdv;

public record ClienteDTO(
        Long id,
        String nome,
        String cpfCnpj
        // Adicione outros campos necessários
) {
}
