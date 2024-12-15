package com.example.travelwise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    @NotBlank(message = "Category name cannot be blank")
    private String name;
    private String description;
    private String icon;
    @NotNull(message = "Department id cannot be null")
    private Long departmentId;
}
