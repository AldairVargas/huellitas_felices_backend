package com.example.huellitas_felices.service;

import com.example.huellitas_felices.dto.CategoryDTO;
import com.example.huellitas_felices.model.Category;
import com.example.huellitas_felices.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category createCategory(CategoryDTO dto) {
        if (categoryRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("La categoría ya existe");
        }
        Category category = Category.builder().nombre(dto.getNombre()).build();
        return categoryRepository.save(category);
    }

    @Transactional
    public Category create(CategoryDTO dto) {
        if (categoryRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("La categoría ya existe");
        }
        Category category = Category.builder().nombre(dto.getNombre()).build();
        return categoryRepository.save(category);
    }

    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada");
        }
        categoryRepository.deleteById(id);
    }
}
