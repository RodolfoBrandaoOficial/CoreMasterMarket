package com.rodolfobrandao.coremastermarket.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class GenericSpecification<T> {

    /**
     * Build specification specification.
     *
     * @param <T>        the type parameter
     * @param qtype      the qtype
     * @param queryValue the query value
     * @param oper       the oper
     * @return the specification
     */
    public static <T> Specification<T> buildSpecification(String qtype, String queryValue, String oper) {
        return (Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            if (qtype == null || queryValue == null || oper == null) {
                return criteriaBuilder.conjunction(); // Always true predicate if no query
            }

            switch (oper) {
                case "=":
                    return criteriaBuilder.equal(root.get(qtype), queryValue);
                case "like":
                    return criteriaBuilder.like(root.get(qtype), "%" + queryValue + "%");
                default:
                    return criteriaBuilder.conjunction(); // No criteria for unknown operation
            }
        };
    }
}
