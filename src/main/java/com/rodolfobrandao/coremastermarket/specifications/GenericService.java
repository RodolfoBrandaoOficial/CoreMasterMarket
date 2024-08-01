package com.rodolfobrandao.coremastermarket.specifications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    public Page<T> findAll(Pageable pageable) {
        return (Page<T>) repository.findAll((Specification<T>) pageable);
    }

    /**
     * Find all page.
     *
     * @param page the page
     * @param oper the oper
     * @param rp the rp
     * @param sortname the sortname
     * @param sortorder the sortorder
     * @return the page
     */
    public Page<T> findAll(int page, String oper, int rp, String sortname, String sortorder) {
        Pageable pageable = PageRequest.of(page - 1, rp, Sort.by(Sort.Order.by(sortname).with(Sort.Direction.fromString(sortorder))));
        return (Page<T>) repository.findAll((Specification<T>) pageable);
    }

}
