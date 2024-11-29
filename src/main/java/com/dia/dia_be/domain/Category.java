package com.dia.dia_be.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long id;

    @Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(20)")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Consulting> consulting = new ArrayList<>();

    private Category(String name) {
        this.name = name;
    }

    @Builder
    public static Category create(String name) {
       return new Category(name);
    }
}
