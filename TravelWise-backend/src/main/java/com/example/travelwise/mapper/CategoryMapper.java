package com.example.travelwise.mapper;

import com.example.travelwise.dto.CategoryDTO;
import com.example.travelwise.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(source = "department.id", target = "departmentId")
    CategoryDTO mapToDTO(Category category);

    @Mapping(source = "departmentId", target = "department.id")
    Category mapToEntity(CategoryDTO categoryDTO);
}
