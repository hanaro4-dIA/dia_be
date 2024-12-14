package com.dia.dia_be.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pb_id")
    private Pb pb;


    @Column(nullable = false, updatable = false)
    private LocalDate date;

    @Column(columnDefinition = "INT UNSIGNED")
    private int count;

    @Column(columnDefinition = "VARCHAR(50)")
    private String memo;

    @Column(nullable = false, updatable = false, columnDefinition = "VARCHAR(20)")
    private String email;

    @Column(nullable = false, updatable = false, columnDefinition = "VARCHAR(30)")
    private String password;

    @Column(nullable = false, updatable = false, columnDefinition = "VARCHAR(20)")
    private String name;

    @Column(nullable = false, updatable = false, columnDefinition = "VARCHAR(20)")
    private String tel;

    @Column(nullable = false, updatable = false, columnDefinition = "VARCHAR(30)")
    private String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JournalKeyword> journalKeyword = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Consulting> consulting = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Notification> notification = new ArrayList<>();

    private Customer(LocalDate date, Long id, int count, String memo, String email, String password, String name, String tel, String address) {
        this.date = date;
        this.id = id;
        this.count = count;
        this.memo = memo;
        this.email = email;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.address = address;
    }

    @Builder
    public static Customer create(Pb pb, LocalDate date, Long id, int count, String memo, String email, String password, String name, String tel, String address) {
        Customer customer= new Customer(date, id, count, memo, email, password, name, tel, address);
        customer.addPb(pb);
        return customer;
    }

    public Customer update(String memo){
        this.memo = memo;
        return this;
    }

    public Customer plusCount(){
        this.count +=1;
        return this;
    }

    private void addPb(Pb pb) {
        this.pb = pb;
        pb.getCustomer().add(this);
    }



}
