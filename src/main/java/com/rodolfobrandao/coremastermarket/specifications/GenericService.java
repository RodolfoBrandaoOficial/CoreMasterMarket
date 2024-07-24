package com.rodolfobrandao.coremastermarket.specifications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class GenericService<T, ID> {
    /**
     * The repository.
     */
    private final JpaSpecificationExecutor<T> repository;

    /**
     * Instantiates a new Generic service.
     *
     * @param repository the repository
     */
    public GenericService(JpaSpecificationExecutor<T> repository) {
        this.repository = repository;
    }

    /**
     * Find all page.
     *
     * @param spec     the spec
     * @param pageable the pageable
     * @return the page
     */
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }
}
