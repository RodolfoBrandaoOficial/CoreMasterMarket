package com.rodolfobrandao.coremastermarket.specifications;

import com.rodolfobrandao.coremastermarket.repositories.tools.GenericQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Scope("prototype")
public class GenericQueryServiceImpl<T> implements GenericQueryService<T> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> entityClass;

    @Autowired
    public GenericQueryServiceImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public List<T> findByQuery(Map<String, Object> queryParams) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);

        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value != null) {
                predicates.add(criteriaBuilder.equal(root.get(key), value));
            }
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
