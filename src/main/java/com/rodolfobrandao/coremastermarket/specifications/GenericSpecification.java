package com.rodolfobrandao.coremastermarket.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class GenericSpecification<T> {

    public static <T> Specification<T> buildSpecification(String qtype, String queryValue, String oper) {
        return (Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            if (qtype == null || queryValue == null || oper == null) {
                return criteriaBuilder.conjunction(); // Sempre verdadeiro se não houver consulta
            }

            switch (oper) {
                case "=":
                    return criteriaBuilder.equal(root.get(qtype), queryValue);
                case "like":
                    return criteriaBuilder.like(root.get(qtype), "%" + queryValue + "%");
                default:
                    return criteriaBuilder.conjunction(); // Nenhum critério para operação desconhecida
            }
        };
    }
}
