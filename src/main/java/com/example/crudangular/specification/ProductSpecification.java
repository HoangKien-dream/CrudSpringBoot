package com.example.crudangular.specification;

import com.example.crudangular.entity.*;
import com.example.crudangular.entity.Order;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;

public class ProductSpecification implements Specification<Product> {
    private SearchCriteria searchCriteria;

    public ProductSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        switch (searchCriteria.getOperator()) {
            case EQUALS:
                if (searchCriteria.getKey().equals("name")){
                    return criteriaBuilder.like(root.get(searchCriteria.getKey()),"%" + searchCriteria.getValue() +"%");
                }
                return criteriaBuilder.equal(
                        root.get(searchCriteria.getKey()),
                        searchCriteria.getValue());
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
            case GREATER_THAN_OR_EQUALS:
                if (root.get(searchCriteria.getKey()).getJavaType() == LocalDate.class) {
                    return criteriaBuilder.greaterThanOrEqualTo(
                            root.get(searchCriteria.getKey()), (LocalDate) searchCriteria.getValue());
                }
                return criteriaBuilder.greaterThanOrEqualTo(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
            case LESS_THAN:
                return criteriaBuilder.lessThan(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
            case LESS_THAN_OR_EQUALS:
                if (root.get(searchCriteria.getKey()).getJavaType() == LocalDate.class) {
                    return criteriaBuilder.lessThanOrEqualTo(
                            root.get(searchCriteria.getKey()), (LocalDate) searchCriteria.getValue());
                }
                return criteriaBuilder.lessThanOrEqualTo(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
        }
        return null;
    }
}
