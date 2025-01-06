package com.example.travelwise.repository;

import com.example.travelwise.entity.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    Page<Offer> findAllByCategoryId(Long categoryId, Pageable pageable);
    Page<Offer> findByCategoryIdAndAvailableSpotsGreaterThan(Long categoryId, int spots, Pageable pageable);
    Page<Offer> findAllByCategoryDepartmentId(Long departmentId, Pageable pageable);

    @Query(value = "SELECT * FROM get_recommended_offers(:targetOfferId, :limitCount)", nativeQuery = true)
    List<Offer> getRecommendedOffers(@Param("targetOfferId") Long targetOfferId, @Param("limitCount") int limitCount);

}
