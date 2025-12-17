package com.miqdad.entity;

import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class ProductSpecification {

    public static Specification<Product> hasNameLike(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) {
                return cb.conjunction(); // tidak menambah kondisi
            }
            String pattern = "%" + name.toLowerCase() + "%";
            return cb.like(cb.lower(root.get("name")), pattern);
        };
    }

    public static Specification<Product> hasCategoryId(Long categoryId) {
        return (root, query, cb) -> {
            if (Objects.isNull(categoryId)) {
                return cb.conjunction();
            }
            return cb.equal(root.get("categoryId"), categoryId);
        };
    }

    public static Specification<Product> isActive(Boolean active) {
        return (root, query, cb) -> {
            if (active == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("isActive"), active);
        };
    }

    // method helper untuk gabung semua filter
    public static Specification<Product> build(String name, Long categoryId, Boolean active) {
    return Specification.allOf(
            hasNameLike(name),
            hasCategoryId(categoryId),
            isActive(active)
    );
}

}
