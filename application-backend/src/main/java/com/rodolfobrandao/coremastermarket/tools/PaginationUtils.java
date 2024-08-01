package com.rodolfobrandao.coremastermarket.tools;

import com.rodolfobrandao.coremastermarket.tools.entities.PaginatedResponse;
import org.springframework.data.domain.Page;

public class PaginationUtils {
    public static <T> PaginatedResponse<T> toPaginatedResponse(Page<T> page) {
        return new PaginatedResponse<>(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getSize(),
                page.getContent()
        );
    }
}
