package com.example.travelwise.specification;

import com.example.travelwise.dto.OfferFiltersDTO;
import com.example.travelwise.entity.Offer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OfferSpecification {
    public static Specification<Offer> filter(OfferFiltersDTO filters) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("id"), filters.getCategoryId()));
            }

            if (filters.getMinPrice() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("price"), filters.getMinPrice())
                );
            }

            if (filters.getMaxPrice() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("price"), filters.getMaxPrice())
                );
            }

            if (filters.getStartsAfter() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("startDate"), filters.getStartsAfter())
                );
            }

            if (filters.getEndsBefore() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("endDate"), filters.getEndsBefore())
                );
            }

            if (filters.getMinAvailableSpots() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("availableSpots"), filters.getMinAvailableSpots())
                );
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
