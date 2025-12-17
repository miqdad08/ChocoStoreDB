package com.miqdad.service;

import lombok.RequiredArgsConstructor;
import com.miqdad.entity.Product;
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

    @Override
    public Product create(ProductRequestDto request) {
        Product product = mapToEntity(request);
        product.setId(null);
        if (product.getIsActive() == null) {
            product.setIsActive(true);
        }
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, ProductRequestDto request) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        existing.setStock(request.getStock());
        existing.setCategoryId(request.getCategoryId());
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
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<Product> search(String name, Long categoryId, Boolean active) {
        Specification<Product> spec = ProductSpecification.build(name, categoryId, active);
        return productRepository.findAll(spec);
    }

    private Product mapToEntity(ProductRequestDto dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .categoryId(dto.getCategoryId())
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
