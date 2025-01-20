package com.example.travelwise.service;

import com.example.travelwise.dto.CategoryDTO;
import com.example.travelwise.entity.Category;
import com.example.travelwise.entity.Department;
import com.example.travelwise.mapper.CategoryMapper;
import com.example.travelwise.repository.CategoryRepository;
import com.example.travelwise.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper, DepartmentRepository departmentRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.departmentRepository = departmentRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::mapToDTO).toList();
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No category with id: " + id));
        return categoryMapper.mapToDTO(category);
    }

    public List<CategoryDTO> getCategoriesByDepartment(Long departmentId) {
        return categoryRepository.findByDepartmentId(departmentId).stream()
                .map(categoryMapper::mapToDTO)
                .toList();
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.mapToEntity(categoryDTO);
        category = categoryRepository.save(category);
        return categoryMapper.mapToDTO(category);
    }

    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
