package com.miqdad.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.miqdad.entity.Product;
import com.miqdad.entity.ProductRequestDto;

public interface ProductService {

    Product create(ProductRequestDto request);

    List<Product> bulkCreate(List<ProductRequestDto> requests);

    Product update(Long id, ProductRequestDto request);

    void delete(Long id);

    Product getById(Long id);

    List<Product> search(String name, Long categoryId, Boolean active);

    void bulkDelete(List<Long> ids);

    Page<Product> getPaged(int page, int size, String name, Long categoryId, Boolean active);
}
