package com.rodolfobrandao.coremastermarket.exceptions;

public record ErrorResponse(String message, org.springframework.http.HttpStatus timestamp, long status, Integer code) {
    /**
     * ErrorResponse
     * Vou usar para manipular o retorno do erro da aplicação
     * @param message
     * @param timestamp
     * @param status
     * @return ErrorResponse
     * @since 1.0
     * @version 1.0
     */

}
