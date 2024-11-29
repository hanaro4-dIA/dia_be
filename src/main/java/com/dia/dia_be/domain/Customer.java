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
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long id;

    @Column(nullable = false, updatable = false, columnDefinition = "VARCHAR(20)")
    private String email;

    @Column(nullable = false, updatable = false, columnDefinition = "VARCHAR(30)")
    private String password;

    @Column(nullable = false, updatable = false, columnDefinition = "VARCHAR(20)")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10)")
    private Sex sex;

    @Column(nullable = false, updatable = false, columnDefinition = "VARCHAR(20)")
    private String tel;

    @Column(nullable = false, updatable = false, columnDefinition = "VARCHAR(30)")
    private String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Customer_pb> customer_pb = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Journal_keyword> journal_keyword = new ArrayList<>();

    private Customer(String email, String password, String name, Sex sex, String tel, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.tel = tel;
        this.address = address;
    }

    @Builder
    public static Customer create(String email, String password, String name, Sex sex, String tel, String address) {
        return new Customer(email,password,name,sex,tel,address);
    }
}
