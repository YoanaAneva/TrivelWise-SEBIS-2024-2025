package com.example.travelwise.repository;

import com.example.travelwise.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByDepartmentId(Long departmentId);
}
