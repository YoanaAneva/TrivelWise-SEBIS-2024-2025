package com.example.travelwise.repository;

import com.example.travelwise.entity.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    Page<Offer> findAllByCategoryId(Long categoryId, Pageable pageable);
    Page<Offer> findAllByCategoryDepartmentId(Long departmentId, Pageable pageable);
}
