package com.rodolfobrandao.coremastermarket.tools.entities;

import java.util.List;

public record PaginatedResponse<T>(long totalElements, int totalPagers, int size, int page, List<T> content) {

/**
 * PaginatedResponse
 */
}
