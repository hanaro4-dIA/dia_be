package com.dia.dia_be.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter(AccessLevel.PACKAGE)
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
    private LocalDate hopeDate;


    @Column(nullable = false, updatable = false)
    private LocalTime hopeTime;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = "DATE")
    private LocalDate reserveDate;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = "TIME")
    private LocalTime reserveTime;

    @Column(nullable = false, columnDefinition = "VARCHAR(500)")
    private String content;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean approve;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "consulting", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Journal journal;

    private Consulting(String title, LocalDate hopeDate, LocalTime hopeTime, LocalDate reserveDate, LocalTime reserveTime, String content, boolean approve) {
        this.title = title;
        this.hopeDate = hopeDate;
        this.hopeTime = hopeTime;
        this.reserveDate = reserveDate;
        this.reserveTime = reserveTime;
        this.content = content;
        this.approve = approve;
    }

    @Builder
    public static Consulting create(Category category, Customer customer, String title, LocalDate hopeDate, LocalTime hopeTime, LocalDate reserveDate, LocalTime reserveTime, String content, boolean approve) {
        Consulting consulting = new Consulting(title,hopeDate, hopeTime,reserveDate,reserveTime,content,approve);
        consulting.addCategory(category);
        consulting.addCustomer(customer);
        consulting.addJournal(new Journal());
        return consulting;
    }

    public Consulting update(String title, String content, boolean approve){
        this.title = title;
        this.content = content;
        this.approve = approve;
        return this;
    }

    private void addCategory(Category category) {
        this.category = category;
        category.getConsulting().add(this);
    }

    private void addCustomer(Customer customer) {
        this.customer = customer;
        customer.getConsulting().add(this);
    }

    private void addJournal(Journal journal) {
        this.journal = journal;
        journal.setConsulting(this);
    }
}
