package com.miqdad.product;

import org.springframework.data.domain.Page;

public interface ProductService {

    Product create(Product product);

    Product update(Long id, Product product);

    void delete(Long id);

    Product getById(Long id);

    Page<Product> getAll(Integer page, Integer size, String name, Long categoryId);
}