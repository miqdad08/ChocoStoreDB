package com.miqdad.service;

import com.miqdad.entity.Category;

import java.util.List;

public interface CategoryService {

    Category create(Category category);

    Category update(Long id, Category category);

    void delete(Long id);

    Category getById(Long id);

    List<Category> getAll();
}
