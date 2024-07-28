package com.rodolfobrandao.coremastermarket.tools;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        return ResponseEntity.status(ex.getStatus()).body(JsonUtil.createMessageJson(ex.getMessage(), ex.getStatus()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JsonUtil.createMessageJson(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
