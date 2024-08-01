package com.rodolfobrandao.coremastermarket.dtos.pdv;

public enum ModoPagamento {
    VOUCHER,
    DINHEIRO,
    CARTAO_DEBITO,
    CARTAO_CREDITO,
    PIX,
    CREDIARIO,
    CHEQUE,
    VRSOFTWARE;

    @Override
    public String toString() {
        return name(); // Usado para obter a representação em string
    }
}
