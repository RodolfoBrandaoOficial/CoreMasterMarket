package com.rodolfobrandao.coremastermarket.tools.entities;

import com.rodolfobrandao.coremastermarket.entities.pdv.Venda;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PaginatedResponse<T> {

    private long totalElements;
    private int totalPages;
    private int size;
    private List<T> content;
    private int page;

    public PaginatedResponse(long totalElements, int totalPages, int size, List<T> content) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.size = size;
        this.content = content;
    }

    public PaginatedResponse(List<Venda> vendas, int size, int size1, int i) {
        this.totalElements = vendas.size();
        this.totalPages = size1;
        this.size = size;
        this.content = (List<T>) vendas;
        this.page = i;
    }


    public void setPage(int page, int size) {
        this.page = page;
        this.size = size;
        this.totalPages = (int) Math.ceil((double) totalElements / size);

    }
}
