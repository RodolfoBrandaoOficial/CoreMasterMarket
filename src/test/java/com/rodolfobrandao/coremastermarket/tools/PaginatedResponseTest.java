package com.rodolfobrandao.coremastermarket.tools;

import com.rodolfobrandao.coremastermarket.tools.entities.PaginatedResponse;
import jdk.jfr.Description;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaginatedResponseTest {


    @Description("""
            E um teste unitario que verifica se a resposta paginada esta correta]
             """)
    @org.junit.jupiter.api.Test
    void testPaginatedResponse() {
        PaginatedResponse<String> paginatedResponse = new PaginatedResponse<>(10, 2, 5, 1, List.of("a", "b", "c", "d", "e"));
        assertEquals(10, paginatedResponse.totalElements());
        assertEquals(2, paginatedResponse.totalPagers());
        assertEquals(5, paginatedResponse.size());
        assertEquals(1, paginatedResponse.page());
        assertEquals(List.of("a", "b", "c", "d", "e"), paginatedResponse.content());
    }

}
