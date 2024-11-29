package com.dia.dia_be.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Consulting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)")
    private String title;

    @Column(nullable = false, updatable = false)
    private LocalDate date;

    @Column(nullable = false, updatable = false)
    private LocalTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_pb_id")
    private Customer_pb customer_pb;

    @OneToOne(mappedBy = "consulting", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Reserve reserve;

    @OneToOne(mappedBy = "consulting", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Journal journal;

    public Consulting(String title, LocalDate date, LocalTime time) {
        this.title = title;
        this.date = date;
        this.time = time;
    }

    @Builder
    public static Consulting create(Category category, Customer_pb customer_pb, String title, LocalDate date, LocalTime time) {
        Consulting consulting = new Consulting(title, date, time);
        consulting.addCategory(category);
        consulting.addCustomer_pb(customer_pb);
        consulting.addReserve(new Reserve());
        consulting.addJournal(new Journal());
        return consulting;
    }

    private void addCategory(Category category) {
        this.category = category;
        category.getConsulting().add(this);
    }

    private void addCustomer_pb(Customer_pb customer_pb) {
        this.customer_pb = customer_pb;
        customer_pb.getConsulting().add(this);
    }

    private void addReserve(Reserve reserve) {
        this.reserve = reserve;
        reserve.setConsulting(this);
    }

    private void addJournal(Journal journal) {
        this.journal = journal;
        journal.setConsulting(this);
    }
}
