package com.example.travelwise.mapper;

import com.example.travelwise.dto.DepartmentDTO;
import com.example.travelwise.entity.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentDTO mapToDTO(Department department);
    Department mapToEntity(DepartmentDTO departmentDTO);
}
