package com.rodolfobrandao.coremastermarket.repositories.tools;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface GenericRepository<T, ID> extends JpaRepository<T, ID> {

    List<T> findByQuery(Map<String, Object> queryParams);
}
