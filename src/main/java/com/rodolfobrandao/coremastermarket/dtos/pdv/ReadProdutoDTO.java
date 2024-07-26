package com.rodolfobrandao.coremastermarket.dtos.pdv;

import java.time.LocalDate;

public record ReadProdutoDTO(String descricao, String codigoBarras, String tipoEmbalagem, String quantidade, String precoVenda) {
    /**
     * ReadProdutoDto
     */
}
