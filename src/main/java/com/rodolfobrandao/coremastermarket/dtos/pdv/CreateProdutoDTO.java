package com.rodolfobrandao.coremastermarket.dtos.pdv;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateProdutoDTO(String codigoBarras, String descricao, BigDecimal precoVenda, String tipoEmbalagem, String quantidade, LocalDate criadoEm, LocalDate atualizadoEm, Long marca, boolean ativo) {
    /**
     * CreateProdutoDTO
     *  - id: Identificador do produto
     *  - codigoBarras: Código de barras do produto
     *  - descricao: Descrição do produto
     *  - precoVenda: Preço de venda do produto
     *  - tipoEmbalagem: Tipo de embalagem do produto
     *  - quantidade: Quantidade do produto
     *  - criadoEm: Data de criação do produto
     *  - atualizadoEm: Data de atualização do produto
     */
}
