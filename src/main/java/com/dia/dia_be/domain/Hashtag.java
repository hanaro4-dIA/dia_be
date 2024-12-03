package com.dia.dia_be.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    private String name;

    private Hashtag(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL)
    private List<Pb_hashtag> pb_hashtag = new ArrayList<>();

    @Builder
    public static Hashtag create(String hashtag) {
        return new Hashtag(hashtag);
    }
}
