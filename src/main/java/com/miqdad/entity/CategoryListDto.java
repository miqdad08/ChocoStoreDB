package com.miqdad.entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryListDto {
    private Long id;
    private String name;
    private String description;
}
