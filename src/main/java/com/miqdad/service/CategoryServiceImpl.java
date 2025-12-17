package com.miqdad.service;

import com.miqdad.entity.Category;
import com.miqdad.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        category.setId(null);
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existing.setName(category.getName());
        existing.setDescription(category.getDescription());

        return categoryRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Checking if category has products
        if (existing.getProducts() != null && !existing.getProducts().isEmpty()) {
            throw new RuntimeException("Cannot delete category with existing products");
        }
        categoryRepository.delete(existing);
    }

    @Override
    public Category getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return category;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

}
