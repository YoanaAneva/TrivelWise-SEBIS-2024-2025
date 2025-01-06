package com.example.travelwise.repository;

import com.example.travelwise.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query(value = "CALL process_cart_payment(:cartId)", nativeQuery = true)
    void processCartPayment(@Param("cartId") Long cartId);
}
