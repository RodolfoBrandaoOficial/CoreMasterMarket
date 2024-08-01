package com.rodolfobrandao.coremastermarket.repositories.tools;

import java.util.List;
import java.util.Map;

public interface GenericQueryService<T> {
    List<T> findByQuery(Map<String, Object> queryParams);
}
