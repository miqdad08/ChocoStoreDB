package com.miqdad.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;              // id : int

    @Column(nullable = false, length = 255)
    private String name;             // name : varchar(255)

    @Column(columnDefinition = "text")
    private String description;      // description : text

    @OneToMany
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonIgnore
    private List<Product> products;

    @Column(name = "created_at")
    private LocalDateTime createdAt; // created_at : timestamp

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
