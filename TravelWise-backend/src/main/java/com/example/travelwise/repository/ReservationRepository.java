package com.example.travelwise.repository;

import com.example.travelwise.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCartId(Long cartId);
    Page<Reservation> findByCartId(Long cartId, Pageable pageable);
    Page<Reservation> findByCartUserId(Long userId, Pageable pageable);
}
