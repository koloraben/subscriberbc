package com.canalplus.subscriberbc.infrastructure.adapters.output.persistence.jpa;

import com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.request.SubscriberSearchCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SubscriberSpecifications {

    public static Specification<SubscriberEntity> getSubscribers(SubscriberSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            addEqualsPredicateIfNotNull(criteria.getMail(), root.get("mail"), predicates, criteriaBuilder);
            addLikePredicateIfNotEmpty(criteria.getFirstName(), root.get("firstName"), predicates, criteriaBuilder);
            addLikePredicateIfNotEmpty(criteria.getLastName(), root.get("lastName"), predicates, criteriaBuilder);
            addEqualsPredicateIfNotNull(criteria.getPhone(), root.get("phone"), predicates, criteriaBuilder);
            addBooleanEqualsPredicate(criteria.isActive(), root.get("isActive"), predicates, criteriaBuilder);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }

    private static <T> void addEqualsPredicateIfNotNull(T value, jakarta.persistence.criteria.Path<T> path, List<Predicate> predicates, jakarta.persistence.criteria.CriteriaBuilder criteriaBuilder) {
        if (value != null) {
            predicates.add(criteriaBuilder.equal(path, value));
        }
    }

    private static void addLikePredicateIfNotEmpty(String value, jakarta.persistence.criteria.Path<String> path, List<Predicate> predicates, jakarta.persistence.criteria.CriteriaBuilder criteriaBuilder) {
        if (value != null && !value.isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(path), "%" + value.toLowerCase() + "%"));
        }
    }

    private static void addBooleanEqualsPredicate(Boolean value, jakarta.persistence.criteria.Path<Boolean> path, List<Predicate> predicates, jakarta.persistence.criteria.CriteriaBuilder criteriaBuilder) {
        if (value != null) {
            predicates.add(criteriaBuilder.equal(path, value));
        }
    }
}
