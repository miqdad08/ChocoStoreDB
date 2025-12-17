package com.miqdad.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.miqdad.entity.Product;
import com.miqdad.entity.ProductRequestDto;
import com.miqdad.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public Product create(@RequestBody ProductRequestDto request) {
        return service.create(request);
    }

    @PostMapping("/bulk")
    public List<Product> bulkCreate(@RequestBody List<ProductRequestDto> requests) {
        return service.bulkCreate(requests);
    }

    @GetMapping
    public List<Product> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean active) {
        return service.search(name, categoryId, active);
    }

    @GetMapping("/paged")
    public Page<Product> getPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean active) {
        return service.getPaged(page, size, name, categoryId, active);
    }

    @GetMapping("/{id}")
    public Product detail(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id,
            @RequestBody ProductRequestDto request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @DeleteMapping("/bulk")
    public void bulkDelete(@RequestBody List<Long> ids) {
        service.bulkDelete(ids);
    }
}
