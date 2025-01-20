package com.example.travelwise.service;

import com.example.travelwise.dto.DepartmentDTO;
import com.example.travelwise.entity.Department;
import com.example.travelwise.mapper.DepartmentMapper;
import com.example.travelwise.repository.CategoryRepository;
import com.example.travelwise.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper, CategoryRepository categoryRepository) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.categoryRepository = categoryRepository;
    }

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream().map(departmentMapper::mapToDTO).toList();
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department newDepartment = departmentRepository.save(departmentMapper.mapToEntity(departmentDTO));
        return departmentMapper.mapToDTO(newDepartment);
    }

    public void deleteDepartment(Long id) {
        categoryRepository.deleteByDepartmentId(id);
        departmentRepository.deleteById(id);
    }
}
