package com.rodolfobrandao.coremastermarket.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodolfobrandao.coremastermarket.tools.entities.ResponseMessage;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter objeto para JSON", e);
        }
    }

    public static String createMessageJson(String message, int status) {
        return toJson(new ResponseMessage(message, status));
    }

}
