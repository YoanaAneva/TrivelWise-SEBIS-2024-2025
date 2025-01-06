package com.example.travelwise.repository;

import com.example.travelwise.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCartIdAndIsPaidFalse(Long cartId);
    Optional<Reservation> findByOfferIdAndIsPaidFalse(Long offerId);
    @Override
    Optional<Reservation> findById(Long aLong);

    Page<Reservation> findByCartId(Long cartId, Pageable pageable);
    Page<Reservation> findByCartUserId(Long userId, Pageable pageable);
    void deleteByCartId(Long cartId);

    @Query(value = "SELECT * FROM get_last_n_offers_by_cart(:cartId, :limitCount)", nativeQuery = true)
    List<Long> getLastNOfferIdsByCart(@Param("cartId") Long cartId, @Param("limitCount") Integer limitCount);
}
