package com.example.travelwise.repository;

import com.example.travelwise.entity.TravelerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelerDetailsRepository extends JpaRepository<TravelerDetails, Long> {
}
