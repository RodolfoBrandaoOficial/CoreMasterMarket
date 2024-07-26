package com.rodolfobrandao.coremastermarket.tools;

import lombok.Data;

import java.util.List;

public record PaginatedResponse<T>(long totalElements, int totalPages, int size, int page, List<T> content) {
    /**
     * PaginatedResponse
     */
}
