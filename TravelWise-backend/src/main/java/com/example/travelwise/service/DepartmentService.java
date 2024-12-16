package com.example.travelwise.service;

import com.example.travelwise.dto.DepartmentDTO;
import com.example.travelwise.mapper.DepartmentMapper;
import com.example.travelwise.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream().map(departmentMapper::mapToDTO).toList();
    }
}
