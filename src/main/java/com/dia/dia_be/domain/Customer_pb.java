package com.dia.dia_be.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Customer_pb {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pb_id")
    private Pb pb;

    @Column(nullable = false, updatable = false)
    private LocalDate date;

    @Column(columnDefinition = "INT UNSIGNED")
    private int count;

    @Column(columnDefinition = "VARCHAR(300)")
    private String memo;

    @OneToMany(mappedBy = "customer_pb", cascade = CascadeType.ALL)
    private List<Consulting> consulting = new ArrayList<>();

    @OneToMany(mappedBy = "customer_pb", cascade = CascadeType.ALL)
    private List<Notification> notification = new ArrayList<>();

    private Customer_pb(LocalDate date, int count, String memo) {
        this.date = date;
        this.count = count;
        this.memo = memo;
    }

    @Builder
    public static Customer_pb create(Customer customer, Pb pb, LocalDate date, int count, String memo) {
        Customer_pb customer_pb = new Customer_pb(date, count, memo);
        customer_pb.addCustomer(customer);
        customer_pb.addPb(pb);
        return customer_pb;
    }

    private void addPb(Pb pb) {
        this.pb = pb;
        pb.getCustomer_pb().add(this);
    }

    private void addCustomer(Customer customer) {
        this.customer = customer;
        customer.getCustomer_pb().add(this);
    }


}
