package com.miqdad.service;

import lombok.RequiredArgsConstructor;

import com.miqdad.entity.Category;
import com.miqdad.entity.Product;
import com.miqdad.repository.CategoryRepository;
import com.miqdad.repository.ProductRepository;
import com.miqdad.entity.ProductRequestDto;
import com.miqdad.entity.ProductSpecification;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product create(ProductRequestDto request) {
        Product product = new Product();
        product.setId(null);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategoryId(request.getCategoryId());
        product.setIsActive(
                request.getIsActive() != null ? request.getIsActive() : true);
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, ProductRequestDto request) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        existing.setStock(request.getStock());
        existing.setCategory(category);
        existing.setIsActive(request.getIsActive());

        return productRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(existing);
    }

    @Override
    public Product getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Category category = categoryRepository.findById(product.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category); // isi field transient

        return product;
    }

    @Override
    public List<Product> search(String name, Long categoryId, Boolean active) {
        Specification<Product> spec = ProductSpecification.build(name, categoryId, active);
        return productRepository.findAll(spec);
    }

    private Product mapToEntity(ProductRequestDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .category(category) // set object Category
                .isActive(dto.getIsActive())
                .build();
    }

    @Override
    public List<Product> bulkCreate(List<ProductRequestDto> requests) {
        List<Product> products = requests.stream()
                .map(this::mapToEntity) // atau panggil mapper
                .peek(p -> {
                    p.setId(null);
                    if (p.getIsActive() == null) {
                        p.setIsActive(true);
                    }
                })
                .toList();
        return productRepository.saveAll(products);
    }

    @Override
    public void bulkDelete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return; // atau lempar exception kalau mau wajib isi
        }
        productRepository.deleteAllById(ids);
    }

    @Override
    public Page<Product> getPaged(int page, int size, String name, Long categoryId, Boolean active) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Specification<Product> spec = ProductSpecification.build(name, categoryId, active);
        return productRepository.findAll(spec, pageable);
    }
}
