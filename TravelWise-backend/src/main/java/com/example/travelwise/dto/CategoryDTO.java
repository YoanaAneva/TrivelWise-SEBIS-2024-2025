package com.example.travelwise.dto;

import com.example.travelwise.entity.Department;
import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private String icon;
    private Long departmentId;
}
