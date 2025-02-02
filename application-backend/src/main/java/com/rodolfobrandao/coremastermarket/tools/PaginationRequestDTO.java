package com.rodolfobrandao.coremastermarket.tools;

import lombok.Data;

@Data
public class PaginationRequestDTO {
    private int page = 1;
    private int size = 1000;
    private String sortname = "id";
    private String sortorder = "asc";
}
