package com.miqdad.controller;

import com.miqdad.entity.Category;
import com.miqdad.entity.CategoryListDto;
import com.miqdad.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public Category create(@RequestBody Category request) {
        return service.create(request);
    }

    @GetMapping
    public List<CategoryListDto> list() {
        return service.getAll().stream()
                .map(c -> {
                    CategoryListDto dto = new CategoryListDto();
                    dto.setId(c.getId());
                    dto.setName(c.getName());
                    dto.setDescription(c.getDescription());
                    return dto;
                })
                .toList();
    }

    @GetMapping("/{id}")
    public Category detail(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id,
            @RequestBody Category request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
